package com.app.dashboard.google;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.social.google.connect.GoogleConnectionFactory;
import org.springframework.social.google.connect.GoogleOAuth2Template;
import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.app.dashboard.google.calendar.CalendarDto;
import com.app.dashboard.google.calendar.CalendarDto.Attendees;
import com.app.dashboard.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * @author 김동건 (dgkim@bsgglobal.com)
 */
@Controller
public class GoogleController {

	private static final Logger logger = LogManager.getLogger(GoogleController.class);
	
	@Autowired
	Environment env;
	
	@Autowired
	Properties properties;
	
	@Autowired
	GoogleConnectionFactory googleConnectionFactory;
	
	@Autowired
	GoogleOAuth2Template googleOAuth2Template;
	
	@Autowired
	OAuth2Parameters googleOAuth2Parameters;
	
	@Autowired
	private UserService userService;
	
	@Value("#{api['api.google.client_id']}")
	private String clientId;
	@Value("#{api['api.google.client_secret']}")
	private String clientSecret;
	@Value("#{api['api.google.token.url']}")
	private String tokenUrl;
	
	@RequestMapping(value = "/googleLogin")
	public String googleLogin(Model model) {

		/* 구글code 발행 */
		OAuth2Operations oauthOperations = googleConnectionFactory.getOAuthOperations();
		String googleUrl = oauthOperations.buildAuthorizeUrl(GrantType.AUTHORIZATION_CODE, googleOAuth2Parameters);

		model.addAttribute("googleUrl", googleUrl);

		return "googleLogin";
	}
	
	@RequestMapping(value = "/googleCallback")
	public String googleCallback(HttpServletRequest request) {
		
		String success = "";
		String failMsg = "";
		try {
			String code = request.getParameter("code");
			
	        //RestTemplate을 사용하여 Access Token 및 profile을 요청
	        RestTemplate restTemplate = new RestTemplate();
	        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
	        parameters.add("code", code);
	        parameters.add("client_id", clientId);
	        parameters.add("client_secret", clientSecret);
	        parameters.add("redirect_uri", googleOAuth2Parameters.getRedirectUri());
	        parameters.add("grant_type", "authorization_code");
	 
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
	        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(parameters, headers);
	        ResponseEntity<Map> responseEntity = restTemplate.exchange(tokenUrl, HttpMethod.POST, requestEntity, Map.class);
	        Map<String, Object> responseMap = responseEntity.getBody();
	 
	        String[] tokens = ((String)responseMap.get("id_token")).split("\\.");
	        String access_token = (String)responseMap.get("access_token");
	        //JSON String 형식을 을 자바 Map 형식으로 변환
	        ObjectMapper mapper = new ObjectMapper();
	        Map<String, String> result = mapper.readValue(new String(Base64.decodeBase64(tokens[1]), "utf-8"), Map.class);
	        
	        Map<String, Object> userMap = new HashMap<String, Object>();
	        userMap.put("user_name", result.get("name"));
	        userMap.put("user_img_url", result.get("picture"));
	        userMap.put("user_email", result.get("email"));
	        userMap.put("user_locale", result.get("locale"));
	        userMap.put("access_token", access_token);
	        userService.insertAndUpdateUser(userMap);
	        
	        HttpSession httpSession = request.getSession(true);
	        httpSession.setMaxInactiveInterval(Integer.parseInt(properties.getProperty("common.sessionTimeout")));
	        httpSession.setAttribute("DASHBOARD_USER_SESSION", userMap);
	        
	        success = "Y";
	        
		} catch(Exception e) {
			e.printStackTrace();
			success = "N";
			failMsg = e.getMessage();
		}
		StringBuffer param = new StringBuffer();
		param.append("?success=" + success);
        param.append("&failMsg=" + failMsg);
		return "redirect:http://localhost/googleResult"+param.toString();
	}
	
	@RequestMapping(value = "/googleResult")
	public String googleResult(@RequestParam("success")String success, @RequestParam("failMsg")String failMsg, Model model) {
		model.addAttribute("success", success);
		model.addAttribute("failMsg", failMsg);
		
		return "googleLogin";
	}
	
	/**
	 * 구글 캘린더 리스트
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getCalendarMeetList")
	public @ResponseBody List<CalendarDto> getCalendarMeetList(HttpServletRequest request){
		HttpSession httpSession = request.getSession(true);
		Map<String, Object> userMap = (Map<String, Object>)httpSession.getAttribute("DASHBOARD_USER_SESSION");
		String access_token = ""+userMap.get("access_token");
		String login_email = ""+userMap.get("user_email");
		
		List<CalendarDto> list = new ArrayList<CalendarDto>();
		//오늘날짜로만 세팅
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date time = new Date();
        String sysdate = format.format(time);
        String timeMin = sysdate+"T00:00:00Z";
        String timeMax = sysdate+"T23:59:59Z";
        try {
        	// 회의실 리스트
        	URL url = new URL("https://www.googleapis.com/calendar/v3/users/me/calendarList?timeMin="+timeMin+"&timeMax="+timeMax+"&timeZone=Asia/Seoul");
        	JsonObject jsonObject = this.getApiCall(url, access_token);
        	JsonArray jsonArray = jsonObject.getAsJsonArray("items");
        	for (JsonElement element : jsonArray) {
        		String calendarId = element.getAsJsonObject().get("id").getAsString();
        		// 회의실별 예약된 회의 리스트
        		if(calendarId.equals(login_email)) {
	        		url = new URL("https://www.googleapis.com/calendar/v3/calendars/"+calendarId+"/events?timeMin="+timeMin+"&timeMax="+timeMax+"&timeZone=Asia/Seoul");
	        		JsonObject jsonMeetObject = this.getApiCall(url, access_token);	// 리스트별 회의일정 가져오기
	        		JsonArray jsonMeetArray = jsonMeetObject.getAsJsonArray("items");
	        		for (JsonElement meetElement : jsonMeetArray) {
	        			CalendarDto calendarDto = new CalendarDto();
	        			calendarDto.setSummary(meetElement.getAsJsonObject().get("summary").getAsString());
	        			calendarDto.setLocation(meetElement.getAsJsonObject().get("location").getAsString());
	        			calendarDto.setStart_datetime(meetElement.getAsJsonObject().get("start").getAsJsonObject().get("dateTime").getAsString());
	        			calendarDto.setEnd_datetime(meetElement.getAsJsonObject().get("end").getAsJsonObject().get("dateTime").getAsString());
	        			
	        			List<CalendarDto.Attendees> AttendeesList = new ArrayList<CalendarDto.Attendees>();
	        			JsonArray jsonAttendeesArray = meetElement.getAsJsonObject().get("attendees").getAsJsonArray();
	        			for (JsonElement attendeesElement : jsonAttendeesArray) {
	        				CalendarDto.Attendees attendees = new CalendarDto().new Attendees();
	        				if(null == attendeesElement.getAsJsonObject().get("resource")) {
	        					attendees.setDisplayName(attendeesElement.getAsJsonObject().get("displayName").getAsString());
		        				attendees.setEmail(attendeesElement.getAsJsonObject().get("email").getAsString());
		            			AttendeesList.add(attendees);
	        				}
	        			}
	        			calendarDto.setAttendeesList(AttendeesList);
	        			list.add(calendarDto);
	        		}
        		}
        	}
        	
        	
        }catch(Exception e) {
        	e.printStackTrace();
        }
        
		return list;
	}
	
	/**
	 * 구글 API Call
	 * @param url : API URL
	 * @param access_token : 사용자 AccessToken
	 * @return
	 */
	private JsonObject getApiCall(URL url, String access_token) throws Exception {
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("Authorization", "Bearer " + access_token);        
        conn.setRequestProperty("Content-Type","application/json");
        conn.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
        String output;

        StringBuffer response = new StringBuffer();
        while ((output = in.readLine()) != null) {
            response.append(output);
        }
        in.close();
        
        JsonObject jsonObject = new JsonParser().parse(response.toString()).getAsJsonObject();
        return jsonObject;
	}
	
	public static void main(String[] args) throws Exception {
		/*
		String aaa = "2020-07-17T15:15:00+09:00";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX");
		LocalDateTime end_datetime = LocalDateTime.parse(aaa, formatter);
		System.out.println(end_datetime);
		System.out.println(end_datetime.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
		*/
	}
}

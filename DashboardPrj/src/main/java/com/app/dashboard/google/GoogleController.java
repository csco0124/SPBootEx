package com.app.dashboard.google;

import java.util.HashMap;
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

import com.app.dashboard.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

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
	 
	        // id_token 라는 키에 사용자가 정보가 존재한다.
	        // 받아온 결과는 JWT (Json Web Token) 형식으로 받아온다. 콤마 단위로 끊어서 첫 번째는 현 토큰에 대한 메타 정보, 두 번째는 우리가 필요한 내용이 존재한다.
	        // 세번째 부분에는 위변조를 방지하기 위한 특정 알고리즘으로 암호화되어 사이닝에 사용한다.
	        //Base 64로 인코딩 되어 있으므로 디코딩한다.
	 
	        String[] tokens = ((String)responseMap.get("id_token")).split("\\.");
	        //JSON String 형식을 을 자바 Map 형식으로 변환
	        ObjectMapper mapper = new ObjectMapper();
	        Map<String, String> result = mapper.readValue(new String(Base64.decodeBase64(tokens[1]), "utf-8"), Map.class);
	        
	        Map<String, Object> userMap = new HashMap<String, Object>();
	        userMap.put("user_name", result.get("name"));
	        userMap.put("user_img_url", result.get("picture"));
	        userMap.put("user_email", result.get("email"));
	        userMap.put("user_locale", result.get("locale"));
	        userService.insertAndUpdateUser(userMap);
	        
	        HttpSession httpSession = request.getSession(true);
	        httpSession.setMaxInactiveInterval(Integer.parseInt(properties.getProperty("common.sessionTimeout")));	//30초
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
	public String googleResult(@RequestParam("success")String success,
			@RequestParam("failMsg")String failMsg,
			Model model) {
		model.addAttribute("success", success);
		model.addAttribute("failMsg", failMsg);
		
		return "googleLogin";
	}
}

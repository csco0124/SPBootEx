package com.app.dashboard.user.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.app.dashboard.user.service.UserService;

@Controller
public class UserController {
	private static final Logger logger = LogManager.getLogger(UserController.class);

	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/getUserSession")
	public @ResponseBody Map<String, Object> getUserSession(HttpServletRequest request) {
		HttpSession httpSession = request.getSession(true);
		Map<String, Object> userMap = new HashMap<String, Object>();
		try {
			if(null != httpSession.getAttribute("DASHBOARD_USER_SESSION")) {
				userMap = (Map<String, Object>)httpSession.getAttribute("DASHBOARD_USER_SESSION");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userMap;
	}
	
	@RequestMapping(value = "/userSessionInvalidate")
	public @ResponseBody String userSessionInvalidate(HttpServletRequest request) {
		HttpSession httpSession = request.getSession(true);
		try {
			httpSession.invalidate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "Y";
	}
	
	
}

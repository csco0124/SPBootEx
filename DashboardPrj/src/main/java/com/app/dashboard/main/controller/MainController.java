package com.app.dashboard.main.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 김동건 (dgkim@bsgglobal.com)
 */
@Controller
public class MainController {

	private static final Logger logger = LogManager.getLogger(MainController.class);
	
	@Autowired
	Environment env;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String main(Locale locale, Model model) {
		logger.info("홈 Welcome Main! The client locale is {}.", locale);
		logger.info("현재환경 {}.", env.getProperty("spring.profiles.active"));
		try {
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "main";
	}
	
	@RequestMapping(value = "/getServerEnv.json")
	public @ResponseBody String getServerEnv() {
		String serverEnv = "";
		try {
			serverEnv = env.getProperty("spring.profiles.active");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return serverEnv;
	}
	
}

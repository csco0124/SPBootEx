package com.app.dashboard.main.controller;

import java.io.File;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.app.dashboard.util.CommonUtil;

/**
 * @author 김동건 (dgkim@bsgglobal.com)
 */
@Controller
public class MainController {

	private static final Logger logger = LogManager.getLogger(MainController.class);
	
	@Autowired
	Environment env;
	
	@Autowired
	Properties properties;
	
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
	
	@RequestMapping("/editorImageUpload")
	public @ResponseBody Map<String, Object> imageUpload(Model model, @RequestParam(value="upload") MultipartFile file) {
		String fileLinkUrl = "";
		try {
			
			String filePath = CommonUtil.createFilePathFolder();
			if(file != null && file.getSize() > 0) {
				String fileName = CommonUtil.getUUID();
				String extension = FilenameUtils.getExtension(file.getOriginalFilename());
				File convFile = new File(filePath.split("\\^\\^\\^")[0] + File.separator + fileName + "." + extension);
				fileLinkUrl = filePath.split("\\^\\^\\^")[1] + "/" + fileName + "." + extension;
				file.transferTo(convFile);
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("uploaded", 1);
		returnMap.put("url", properties.getProperty("common.fileLinkPath") + "/" + fileLinkUrl);
		return returnMap;
	}
	
}

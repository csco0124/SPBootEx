package com.app.dashboard.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * 공통유틸
 * @author 김동건 (dgkim@bsgglobal.com)
 */
@Configuration
public class CommonUtil {
	
	private static Environment env;
	private static Properties properties;

	@Autowired
	public void setEnv(Environment env) {
		CommonUtil.env = env;
	}
	
	@Autowired
	public void setProperties(Properties properties) {
		CommonUtil.properties = properties;
	}
	
	/**
	 * 파일업로드 위한 새폴더 생성
	 * @return 파일 전체경로^^^파일 링크경로 (split 으로 잘라서 활용 가능)
	 */
	public static String createFilePathFolder() {
		String path = properties.getProperty("common.filepath");
		//오늘 날짜 가져오기
		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String dateFolder = sdf.format(dt).toString();
		File dir = new File(path, dateFolder);
		
		//폴더가 없으면 생성     
		if(!dir.exists()){
			dir.mkdirs();
		}
		return dir.getAbsolutePath() + "^^^" + dateFolder;
	}
	
	/**
	 * 랜덤값(밀리세컨드-범용 고유 식별자) 가져오기
	 * @return
	 */
	public static String getUUID() {
		return System.currentTimeMillis() + "-" + UUID.randomUUID().toString();
	}
}

package com.app.dashboard.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author 김동건 (dgkim@bsgglobal.com)
 */
public class SessionInterceptor implements HandlerInterceptor{
	
	private static final Logger logger = LogManager.getLogger(SessionInterceptor.class);
	
	/**
	 * 컨트롤러가 호출되기 전에 실행된다.
	 *  - 리턴 값이 true 이면 핸들러 실행 체인의 다음 단계로 진행되지만, false 라면 작업을 중단하고 리턴하므로 컨트롤러와 남은 인터셉터들은 실행되지 않는다.
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		logger.info("SessionInterceptor... preHandle...");
		
		return true;
		
	}
	
	/**
	 * 컨트롤러를 실행하고 난 후에 호출된다.
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		logger.info("SessionInterceptor... postHandle...");
	}
	
	/**
	 * 클라이언트 요청 처리뒤 클리이언트에 뷰를 통해 응답을 전송한뒤 실행 된다. 뷰를 생성항때 예외가 발생해도 실행된다.
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		logger.info("SessionInterceptor... afterCompletion...");
	}

}

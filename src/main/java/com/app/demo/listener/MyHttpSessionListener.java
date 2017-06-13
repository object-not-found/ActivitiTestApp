package com.app.demo.listener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * 自定义listener与自定义Servlet情况类似，
 * 在使用注解的情况下都需要在启动类中开启@ServletComponentScan注解
 * 不使用注解的情况下需要在启动类中注入ServletListenerRegistrationBean
 * @author yuzhiyou
 *
 */
@WebListener
public class MyHttpSessionListener implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		// TODO Auto-generated method stub
		System.out.println("=========MyHttpSessionListener.sessionCreated()=========");
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		// TODO Auto-generated method stub
		System.out.println("=========MyHttpSessionListener.sessionDestroyed()=========");
	}

}

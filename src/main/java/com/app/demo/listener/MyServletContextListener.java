package com.app.demo.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * 自定义listener与自定义Servlet情况类似，
 * 在使用注解的情况下都需要在启动类中开启@ServletComponentScan注解
 * 不使用注解的情况下需要在启动类中注入ServletListenerRegistrationBean
 * @author yuzhiyou
 *
 */
@WebListener
public class MyServletContextListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		System.out.println("=========MyServletContextListener.contextInitialized()=========");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		System.out.println("=========MyServletContextListener.contextDestroyed()=========");
	}

}

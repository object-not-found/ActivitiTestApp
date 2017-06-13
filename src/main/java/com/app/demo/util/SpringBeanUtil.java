package com.app.demo.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 用于普通类调用容器中的bean，需放到扫描目录下，否则需要在Application.java中显示注入，
 * 可以使用@Bean、@Import、@Resource、@Autowired
 * @author yuzhiyou
 *
 */
@Component
public class SpringBeanUtil implements ApplicationContextAware {
	private static ApplicationContext applicationContext = null;
	@Override
	public void setApplicationContext(ApplicationContext arg0) throws BeansException {
		// TODO Auto-generated method stub
		if(SpringBeanUtil.applicationContext == null){
			applicationContext = arg0;
		}
	}
	
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public static Object getBean(String beanName){
		return getApplicationContext().getBean(beanName);
	}
	
	public static <T> T getBean(Class<T> t){
		return getApplicationContext().getBean(t);
	}
	
	public static <T> T getBean(String beanName, Class<T> t){
		return getApplicationContext().getBean(beanName, t);
	}
}

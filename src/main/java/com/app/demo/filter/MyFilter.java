package com.app.demo.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

/**
 * 自定义filter与自定义Servlet情况类似，
 * 在使用注解的情况下都需要在启动类中开启@ServletComponentScan注解
 * 不使用注解的情况下需要在启动类中注入FilterRegistrationBean
 * @author yuzhiyou
 *
 */
@WebFilter(filterName="myFilter", urlPatterns="/*")
public class MyFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		System.out.println("============MyFilter.init()==========");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		System.out.println("============MyFilter.doFilter()==========");
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		System.out.println("============MyFilter.destroy()==========");
	}

}

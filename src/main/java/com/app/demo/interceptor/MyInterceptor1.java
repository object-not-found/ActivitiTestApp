package com.app.demo.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class MyInterceptor1 implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		System.out.println("=============MyInterceptor1.preHandle()  在请求处理之前调用，即在Controller方法调用之前===============");
		return true;  //只有返回true才会继续向下执行，返回false取消当前请求
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("===================MyInterceptor1.postHandle()  请求处理之后调用，但是在视图渲染之前调用(Controller方法调用之后)======================");
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		System.out.println("=============MyInterceptor1.afterCompletion()  在整个请求结束之后被调用，也就是在DispatcherServlet渲染了对应的视图之后执行(主要是为了进行资源清理工作)===========");
	}

}

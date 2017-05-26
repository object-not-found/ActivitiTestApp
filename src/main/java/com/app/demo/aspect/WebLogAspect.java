package com.app.demo.aspect;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Order(5)
@Component
public class WebLogAspect {
	private Logger logger = Logger.getLogger(getClass());
	ThreadLocal<Long> startTime = new  ThreadLocal<>();
	
	@Pointcut("execution(public * com.app.demo.controller..*.*(..))")
	public void log(){}
	
	@Before("log()")
	public void doBefore(JoinPoint joinPoint){
		startTime.set(System.currentTimeMillis());
		
		//获取接收到的请求
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
		
		//记录请求内容
		logger.info("URL:" + request.getRequestURL().toString());
		logger.info("HTTP_METHOD:" + request.getMethod());
		logger.info("IP:" + request.getRemoteAddr());
		logger.info("CLASS_METHOD:" + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
		logger.info("ARGS:" + Arrays.toString(joinPoint.getArgs()));
	}
	
	@AfterReturning(returning="ret", pointcut="log()")
	public void doAfterReturning(Object ret){
		//请求处理完毕，返回内容
		logger.info("RESPONSE:" + ret);
		logger.info("SPEND TIME:" + (System.currentTimeMillis() - startTime.get()));
	}
}

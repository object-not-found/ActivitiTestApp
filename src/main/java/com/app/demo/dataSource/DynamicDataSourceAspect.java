package com.app.demo.dataSource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 切换数据源Advice
 * 使用aop技术在执行事务方法前进行数据源的切换
 * @author yuzhiyou
 *
 */
@Aspect
@Order(-10)  //保证该AOP在@Transaction之前执行
@Component
public class DynamicDataSourceAspect {
	
	/**
	 * 在方法之前执行，用于拦截注解了targetDataSource的方法，否则不拦截
	 * @param point
	 * @param targetDataSource
	 */
	@Before("@annotation(targetDataSource)")
	public void changeDataSource(JoinPoint point, TargetDataSource targetDataSource){
		//获取指定的数据源
		String dataSourceId = targetDataSource.value();
		//如果指定的数据源存在则进行切换，否则使用默认数据源
		if(DynamicDataSourceContextHolder.containsDataSource(dataSourceId)){
			//TODO 此处应记录切换数据源的日志
			DynamicDataSourceContextHolder.setDataSourceType(dataSourceId);
		}else{
			//TODO 找不到指定数据源，使用系统默认数据源
		}
		
	}
	
	@After("@annotation(targetDataSource)")
	public void restoreDataSource(JoinPoint point, TargetDataSource targetDataSource){
		//方法执行完后销毁数据源信息，进行垃圾回收
		DynamicDataSourceContextHolder.clearDataSourceType();
	}
}

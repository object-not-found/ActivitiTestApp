package com.app.demo.dataSource;

import java.util.ArrayList;
import java.util.List;

/**
 * 动态数据源上下文
 * 管理数据源的线程池
 * @author yuzhiyou
 *
 */
public class DynamicDataSourceContextHolder {

	/**
	 * 当使用ThreadLocal维护变量时，ThreadLocal为每个使用该变量的线程提供独立的变量副本，
	 * 所以每一个线程都可以独立的改变自己的副本，而不会影响其他线程所对应的副本。
	 */
	private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();
	
	/**
	 * 管理所有的数据源ID，主要是为了判断数据源是否存在
	 */
	public static List<String> dataSourceIds = new ArrayList<>();
	
	/**
	 * 设置当前的数据源
	 * @param dataSource
	 */
	public static void setDataSourceType(String dataSourceType){
		contextHolder.set(dataSourceType);
	}
	
	/**
	 * 获取当前数据源
	 * @return
	 */
	public static String getDataSourceType(){
		return contextHolder.get();
	}
	
	/**
	 * 清除当前数据源
	 */
	public static void clearDataSourceType(){
		contextHolder.remove();
	}
	
	/**
	 * 判断数据源是否存在
	 * @param dataSource
	 * @return
	 */
	public static boolean containsDataSource(String dataSource){
		return dataSourceIds.contains(dataSource);
	}
	
	
	
	
	
	
}

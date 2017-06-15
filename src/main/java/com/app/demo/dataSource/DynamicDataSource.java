package com.app.demo.dataSource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 动态数据源的自动切换
 * 动态数据源能够进行自动切换的核心就是spring底层提供了AbstractRoutingDataSource类进行数据源
 * 的路由的，我们主要继承这个类并实现里面的方法即可实现我们想要的，主要实现方法是determineCurrentLookupKey()
 * @author yuzhiyou
 *
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

	/**
	 * 该方法返回数据源的名称，并交由AbstractRoutingDataSource进行注入使用
	 */
	@Override
	protected Object determineCurrentLookupKey() {
		return DynamicDataSourceContextHolder.getDataSourceType();
	}

}

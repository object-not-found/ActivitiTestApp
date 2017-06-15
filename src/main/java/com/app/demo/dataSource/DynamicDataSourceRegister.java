package com.app.demo.dataSource;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.bind.RelaxedDataBinder;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;

/**
 * 动态数据源的注册
 * @author yuzhiyou
 *
 */
public class DynamicDataSourceRegister implements ImportBeanDefinitionRegistrar, EnvironmentAware {

	//默认数据源类型
	private static final Object DATASOURCE_DEFAULT = "org.apache.tomcat.jdbc.pool.DataSource";  
	private ConversionService conversionService = new DefaultConversionService();
	private PropertyValues dataSourcePropertyValues;
	//默认数据源
	private DataSource defaultDataSource;
	private Map<String, DataSource> customDataSources = new HashMap<>();
	
	/**
	 * 多数据源配置
	 */
	@Override
	public void setEnvironment(Environment environment) {
		// TODO Auto-generated method stub
		initDefaultDataSource(environment);
		initCustomDataSource(environment);
	}

	/**
	 * 默认数据源设置
	 * @param environment
	 */
	private void initDefaultDataSource(Environment environment) {
		//从配置文件读取主数据源配置信息
		RelaxedPropertyResolver propertyResolver = new RelaxedPropertyResolver(environment, "spring.datasource.");
		Map<String, Object> dsMap = new HashMap<>();
		dsMap.put("type", propertyResolver.getProperty("type"));
		dsMap.put("driverClassName", propertyResolver.getProperty("driverClassName"));
		dsMap.put("url", propertyResolver.getProperty("url"));
		dsMap.put("username", propertyResolver.getProperty("username"));
		dsMap.put("password", propertyResolver.getProperty("password"));
		
		//创建数据源
		defaultDataSource = buildDataSource(dsMap);
		dataBinder(defaultDataSource, environment);
	}

	/**
	 * 多数据源设置
	 * @param environment
	 */
	private void initCustomDataSource(Environment environment) {
		RelaxedPropertyResolver propertyResolver = new RelaxedPropertyResolver(environment, "custom.datasource.");
		String prefixs = propertyResolver.getProperty("names");
		for(String prefix: prefixs.split(",")){
			Map<String, Object> dsMap = propertyResolver.getSubProperties(prefix+".");
			DataSource ds = buildDataSource(dsMap);
			customDataSources.put(prefixs, ds);
			dataBinder(ds, environment);
		}
	}

	/**
	 * 数据源创建
	 * @param dsMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private DataSource buildDataSource(Map<String, Object> dsMap) {
		Object type = dsMap.get("type");
		if(type == null){
			type = DATASOURCE_DEFAULT;
		}
		Class<? extends DataSource> dataSource;
		try{
			dataSource = (Class<? extends DataSource>) Class.forName((String) type);
			String driverClassName = dsMap.get("driverClassName").toString();
			String url = dsMap.get("url").toString();
			String username = dsMap.get("username").toString();
			String password = dsMap.get("password").toString();
			
			DataSourceBuilder factory = DataSourceBuilder.create().driverClassName(driverClassName).url(url).username(username).password(password);
			return factory.build();
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 数据源绑定
	 * @param dataSource
	 * @param environment
	 */
	private void dataBinder(DataSource dataSource, Environment environment) {
		RelaxedDataBinder dataBinder = new RelaxedDataBinder(dataSource);
		dataBinder.setConversionService(conversionService);
		dataBinder.setIgnoreNestedProperties(false);
		dataBinder.setIgnoreInvalidFields(false);
		dataBinder.setIgnoreUnknownFields(true);
		
		if(dataSourcePropertyValues == null){
			Map<String, Object> rp = new RelaxedPropertyResolver(environment, "spring.datasource").getSubProperties(".");
			Map<String, Object> vm = new HashMap<>(rp);
			vm.remove("type");
			vm.remove("driverClassName");
			vm.remove("url");
			vm.remove("username");
			vm.remove("password");
			dataSourcePropertyValues = new MutablePropertyValues(vm);
		}
		dataBinder.bind(dataSourcePropertyValues);
	}

	/**
	 * 注册
	 */
	@Override
	public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
		Map<Object, Object> targetDataSources = new HashMap<>();
		targetDataSources.put("dataSource", defaultDataSource);
		DynamicDataSourceContextHolder.dataSourceIds.add("dataSource");
		
		targetDataSources.putAll(customDataSources);
		for(String key: customDataSources.keySet()){
			DynamicDataSourceContextHolder.dataSourceIds.add(key);
		}
		
		GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
		beanDefinition.setBeanClass(DynamicDataSource.class);
		
		beanDefinition.setSynthetic(true);
		
		MutablePropertyValues mpv = beanDefinition.getPropertyValues();
		mpv.addPropertyValue("defaultTargetDataSource", defaultDataSource);
		mpv.add("targetDataSources", targetDataSources);
		registry.registerBeanDefinition("dataSource", beanDefinition);
		
	}

}

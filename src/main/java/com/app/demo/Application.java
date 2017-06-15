package com.app.demo;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.app.demo.dataSource.DynamicDataSourceRegister;

@SpringBootApplication
@ServletComponentScan
@Import({DynamicDataSourceRegister.class})  //注册多数据源
public class Application extends WebMvcConfigurerAdapter {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SpringApplication.run(Application.class, args);
	}

	/**
	 * fastjson解析json数据
	 * 第一种方法：需启动类继承WebMvcConfigurerAdapter并重写configureMessageConverters方法
	 */
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		super.configureMessageConverters(converters);
		FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
		FastJsonConfig fastJsonConfig = new FastJsonConfig();
		fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
		fastConverter.setFastJsonConfig(fastJsonConfig);
		converters.add(fastConverter);
	}
	
	/**
	 * fastjson解析json数据
	 * 第二种方法：无须集成任何类
	 */
//	@Bean
//	public HttpMessageConverters fastJsonHttpMessageConverters(){
//		FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
//		FastJsonConfig fastJsonConfig = new FastJsonConfig();
//		fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
//		fastConverter.setFastJsonConfig(fastJsonConfig);
//		HttpMessageConverter<?> convert = fastConverter;
//		return new HttpMessageConverters(convert);
//	}
	
	/**
	 * 注册自定义Servlet
	 */
//	@Bean
//	public ServletRegistrationBean MyServlet(){
//		return new ServletRegistrationBean(new com.app.demo.servlet.MyServlet(), "/myServlet/*");
//	}
	
}

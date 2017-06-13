package com.app.demo.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.app.demo.interceptor.MyInterceptor1;

@Configuration
public class MyWebAppConfigurer extends WebMvcConfigurerAdapter {

	/**
	 * 自定义拦截器的使用
	 * 自定义一个拦截器类，实现HandlerInterceptor接口，
	 * 然后创建一个java类继承WebMvcConfigurerAdapter，并重写addInterceptors方法，
	 * 实例化自定义的拦截器，然后将对象手动添加到拦截器链中（在addInterceptors方法中添加）
	 * 
	 * 只有经过DispatcherServlet的请求，才会走拦截器链，自定义的Servlet请求不会被拦截的，比如MyServlet，
	 * 而对于过滤器来说，不管属于哪个Servlet只要符合过滤器的过滤规则，过滤器都会对其拦截
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// TODO Auto-generated method stub
		//多个拦截器组成一个拦截器链
		// addPathPatterns  用于添加拦截规则
		// excludePathPatterns  用户排除拦截
		registry.addInterceptor(new MyInterceptor1()).addPathPatterns("/**");
		super.addInterceptors(registry);
	}
	
}

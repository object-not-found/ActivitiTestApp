package com.app.demo.filter;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.springframework.context.annotation.Configuration;

import com.app.demo.conf.BaseShiroConfiguration;

@Configuration
public class ShiroConfiguration extends BaseShiroConfiguration {

	@Override
	protected void loadShiroFilterChain(ShiroFilterFactoryBean factoryBean) {
		// TODO Auto-generated method stub
		Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
		filterChainDefinitionMap.put("/activiti/login", "anon");
		filterChainDefinitionMap.put("/activiti/logout", "anon");
		filterChainDefinitionMap.put("/activiti/user/**", "userAuth");
		
		filterChainDefinitionMap.put("/**", "anon");
		
		factoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
	}

}

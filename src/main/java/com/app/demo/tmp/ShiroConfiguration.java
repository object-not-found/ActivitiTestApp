package com.app.demo.tmp;

import com.gomeplus.meipro.web.config.BaseShiroConfiguration;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * shiro 配置
 *
 * @author qupeng
 *
 */
@Configuration
public class ShiroConfiguration extends BaseShiroConfiguration {

    @Resource
    private ApplicationContext applicationContext;

    /**
     * 加载权限
     */
    @Override
    protected void loadShiroFilterChain(ShiroFilterFactoryBean shiroFilterFactoryBean) {

        shiroFilterFactoryBean.getFilters().put("custom", applicationContext.getBean(CustomFilter.class));

        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/logout", "anon");
        filterChainDefinitionMap.put("/login", "anon");
        filterChainDefinitionMap.put("/v*/user/user_info", "userAuth");
        filterChainDefinitionMap.put("/v*/dept/**", "userAuth");
        filterChainDefinitionMap.put("/v*/staff/**", "userAuth");
        filterChainDefinitionMap.put("/v*/access_authorize", "userAuth");
		filterChainDefinitionMap.put("/v*/common/**", "userAuth");

        filterChainDefinitionMap.put("/**", "anon");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
    }

    @Bean
    public CustomFilter customFilter(){
        return new CustomFilter();
    }

}
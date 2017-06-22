package com.app.demo.conf;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.app.demo.filter.RolesAuthFilter;
import com.app.demo.filter.UserAuthFilter;

/**
 * shiro配置
 * shiro核心通过filter实现，类似于SpringMVC的dispatchServlet一样进行控制，
 * filter通过URL规则进行过滤和权限检测，所以要定义一系列关于URL的规则和访问权限
 * @author yuzhiyou
 *
 */
//@Configuration
public abstract class BaseShiroConfiguration {

	@Bean
	public ShiroRealm shiroRealm(){
		ShiroRealm realm = new ShiroRealm();
//		realm.setCredentialsMatcher(hashedCredentialsMatcher());
		return realm;
	}
	
	public HashedCredentialsMatcher hashedCredentialsMatcher(){
		HashedCredentialsMatcher hcm = new HashedCredentialsMatcher();
		hcm.setHashAlgorithmName("md5");  //使用MD5加密
		hcm.setHashIterations(2);  //加密两次
		return hcm;
	}
	
	/**
	 * 处理拦截资源文件问题
	 * 注意：需要配置SecurityManager的bean，并注入到处理方法中
	 * Filter chain说明：
	 * 1.一个URL可以配置多个Filter，使用逗号分隔
	 * 2.当设置多个过滤时，全部通过才视为通过
	 * 3.部分过滤器可指定参数，如perms、roles
	 * @param securityManager
	 * @return
	 */
	@Bean
	public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager){
		ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
		//必须设置SecurityManager
		factoryBean.setSecurityManager(securityManager);
		
		//拦截器
//		Map<String, String> map = new HashMap<>();
//		//退出拦截器，具体实现代码Shiro已经实现
//		map.put("/logout", "logout");
//		//过滤链定义，从上而下顺序执行，一般将"/***"放在最下边
//		//authc：所有url都必须认证通过才可以访问；anon：所有url都可以匿名访问
//		map.put("/***", "authc");
//		
//		//默认自动寻找web工程根目录下的"/login.jsp"页面
//		factoryBean.setLoginUrl("/login");
//		//登陆成功后要跳转的链接
//		factoryBean.setSuccessUrl("/index");
//		//未授权界面
//		factoryBean.setUnauthorizedUrl("/403");
//		
//		factoryBean.setFilterChainDefinitionMap(map);
		
		factoryBean.getFilters().put("userAuth", new UserAuthFilter());
		factoryBean.getFilters().put("roleAuth", new RolesAuthFilter());
		
		loadShiroFilterChain(factoryBean);
		
		return factoryBean;
	}
	
	protected abstract void loadShiroFilterChain(ShiroFilterFactoryBean factoryBean);

	/**
	 * 核心安全事务管理器
	 * @return
	 */
	@Bean
	public SecurityManager securityManager(){
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setRealm(shiroRealm());
		return securityManager;
	}
}

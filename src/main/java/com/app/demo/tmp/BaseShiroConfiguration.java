package com.app.demo.tmp;

import com.gomeplus.meipro.common.utils.Constants;
import com.gomeplus.meipro.web.advisor.CustomAuthorizationAttributeSourceAdvisor;
import com.gomeplus.meipro.web.security.AuthorizationListener;
import com.gomeplus.meipro.web.security.MeiproRealm;
import com.gomeplus.meipro.web.security.shiro.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.pam.FirstSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.session.mgt.SessionFactory;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.filter.DelegatingFilterProxy;
import redis.clients.jedis.JedisCluster;

/**
 * shiro 配置
 *
 * @author qupeng
 *
 */
@Configuration
public abstract class BaseShiroConfiguration {

	private static final Logger logger = LoggerFactory.getLogger(BaseShiroConfiguration.class);

	@Bean
	public RedisCacheManager getRedisCacheManager(JedisCluster jedisCluster) {
		RedisCacheManager redisCacheManager = new RedisCacheManager(jedisCluster);
		return redisCacheManager;
	}

	@Bean(name = "meiproRealm")
	public MeiproRealm getMeiproRealm() {
		MeiproRealm meiproRealm = new MeiproRealm();
//		loginRealm.setCacheManager(redisCacheManager);
		//认证缓存默认为false
//		loginRealm.setAuthenticationCachingEnabled(false);
		//授权缓存默认为true
//		meiproRealm.setAuthorizationCachingEnabled(true);
		meiproRealm.setCredentialsMatcher(hashedCredentialsMatcher());
		return meiproRealm;
	}

	public HashedCredentialsMatcher hashedCredentialsMatcher(){
		HashedCredentialsMatcher hcm = new HashedCredentialsMatcher();
		hcm.setHashAlgorithmName(Sha256Hash.ALGORITHM_NAME);
		hcm.setHashIterations(1024);
		//hcm.setStoredCredentialsHexEncoded(true);
		return hcm;
	}

	/**
	 * 注册 DelegatingFilterProxy，参考xml格式
	 * @return
	 */
	@Bean
	public FilterRegistrationBean filterRegistrationBean() {
		DelegatingFilterProxy proxy = new DelegatingFilterProxy();
		proxy.setTargetBeanName("shiroFilter");
		FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
		filterRegistration.setFilter(proxy);
		// 该值缺省为false,表示生命周期由SpringApplicationContext管理,设置为true则表示由ServletContainer管理
		filterRegistration.addInitParameter("targetFilterLifecycle", "true");
		filterRegistration.setEnabled(true);
		filterRegistration.addUrlPatterns("/*");
		return filterRegistration;
	}

	/**
	 * 确保实现了Shiro内部lifecycle函数的bean执行
	 *
	 * @return
	 */
	@Bean(name = "lifecycleBeanPostProcessor")
	public static LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}

	/**
	 * 开启Shiro的注解
	 *
	 * @return
	 */
	@Bean
	public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator proxy = new DefaultAdvisorAutoProxyCreator();
		proxy.setProxyTargetClass(true);
		return proxy;
	}

	@Bean
	public CustomAuthorizationAttributeSourceAdvisor getCustomAuthorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
		CustomAuthorizationAttributeSourceAdvisor advisor = new CustomAuthorizationAttributeSourceAdvisor();
		advisor.setSecurityManager(securityManager);
		return advisor;
	}

	@Bean
	public SessionDAO getSessionDao(JedisCluster jedisCluster) {
		RedisSessionDAO dao = new RedisSessionDAO(jedisCluster);
//        dao.setSessionIdGenerator(new ShiroSessionIdGenerator(jedisCluster));
		//the SessionDAO implements the CacheManagerAware interface.
		// it will automatically be supplied with any available globally configured CacheManager
//		dao.setCacheManager(new RedisCacheManager(jedisCluster));
		return dao;
	}

	@Bean(name = "securityManager")
	public DefaultWebSecurityManager getDefaultWebSecurityManager(SessionManager defaultSessionManager, RedisCacheManager redisCacheManager, MeiproRealm meiproRealm, CookieRememberMeManager cookieRememberMeManager) {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		ModularRealmAuthenticator mr =  new ModularRealmAuthenticator();
		mr.setAuthenticationStrategy(new FirstSuccessfulStrategy());
		securityManager.setAuthenticator(mr);
		securityManager.setRealm(meiproRealm);
		// 用自定义的Factory实现替换默认
		// 用于关闭session功能
		//securityManager.setSubjectFactory(new StatelessSubjectFactory());
		securityManager.setSessionManager(defaultSessionManager);
		// 关闭session存储
		//((DefaultSessionStorageEvaluator) ((DefaultSubjectDAO) securityManager.getSubjectDAO())
		//		.getSessionStorageEvaluator()).setSessionStorageEnabled(false);

		// 用户授权/认证信息Cache, 采用redis 缓存
		//此处将cacheManager赋值给securityManager、sessionManager、sessionDAO
		securityManager.setCacheManager(redisCacheManager);
		//securityManager.setCacheManager(redisCacheManager);
		SecurityUtils.setSecurityManager(securityManager);
		securityManager.setRememberMeManager(cookieRememberMeManager);
		return securityManager;
	}

	@Bean
	public SessionManager defaultSessionManager(SessionDAO sessionDAO) {
		CustomWebSessionManager manager = new CustomWebSessionManager();
		manager.setSessionDAO(sessionDAO);
		manager.setSessionIdCookie(getCookie());
		manager.setSessionFactory(getShiroSessionFactory());
//		manager.setSessionIdCookieEnabled(false);
        manager.setSessionValidationSchedulerEnabled(false);
//		manager.setSessionValidationInterval(60 * 1000);
		//session timeout
		// 		manager.setGlobalSessionTimeout(60*1000);
  		return manager;
	}

	public SessionFactory getShiroSessionFactory() {
  		return new ShiroSessionFactory();
	}
	@Bean
	public CookieRememberMeManager getCookieRememberMeManager(){
		CookieRememberMeManager crm =  new CookieRememberMeManager();
		crm.setCookie(getRememberMeCookie());
		return crm;
	}
	/**
	 * 配置ShiroFilter
	 */
	@Bean(name = "shiroFilter")
	public ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultWebSecurityManager securityManager) {

		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		// 必须设置 SecurityManager
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		// login url
		//shiroFilterFactoryBean.setLoginUrl("/login");
		// 登录成功后要跳转的连接
//		shiroFilterFactoryBean.setSuccessUrl("/index");
//		shiroFilterFactoryBean.setUnauthorizedUrl("/403");
		//access_token
		/**
		 * 增加自定义的filter，加载顺序参考DefaultFilter
		 * shiroFilterFactoryBean.getFilters().put("custom-filter", custom
		 * extended AccessControlFilter filter);
		 */
        shiroFilterFactoryBean.getFilters().put("userAuth", new UserAuthFilter());
        shiroFilterFactoryBean.getFilters().put("roleAuth", new RolesAuthFilter());
 		loadShiroFilterChain(shiroFilterFactoryBean);
		return shiroFilterFactoryBean;
	}

	public SimpleCookie getCookie(){
		SimpleCookie workCookie = new SimpleCookie();
		workCookie.setName("sid");
		workCookie.setPath("/");
		workCookie.setHttpOnly(false);
		return workCookie;
	}

	public SimpleCookie getRememberMeCookie(){
		SimpleCookie rememberMeCookie = new SimpleCookie();
		rememberMeCookie.setName("rm_sid");
		rememberMeCookie.setPath("/");
		rememberMeCookie.setMaxAge(Constants.REMEMBER_ME_EXPIRE_TIME);
		rememberMeCookie.setHttpOnly(true);
		return rememberMeCookie;
	}
    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(25);
        return executor;
    }

	@Bean
	public AuthorizationListener listener() {
		return new AuthorizationListener();
	}

 	/**
	 * 加载权限
	 */
	protected abstract void loadShiroFilterChain(ShiroFilterFactoryBean shiroFilterFactoryBean);

}
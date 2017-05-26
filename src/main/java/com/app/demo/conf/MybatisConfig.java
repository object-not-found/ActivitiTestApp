package com.app.demo.conf;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@MapperScan("com.app.demo.dao")
@EnableTransactionManagement
public class MybatisConfig {

	private static final String MAPPER_XML = "classpath*:com/app/demo/mapper/*.xml";
	private static final String MAPPER_DOMAIN = "com.app.demo.domain";
	@Bean
	public SqlSessionFactory sqlSessionFactory(DataSource ds) throws Exception{
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(ds);
		PathMatchingResourcePatternResolver pmr = new PathMatchingResourcePatternResolver();
		sqlSessionFactoryBean.setMapperLocations(pmr.getResources(MAPPER_XML));
		sqlSessionFactoryBean.setTypeAliasesPackage(MAPPER_DOMAIN);
		return sqlSessionFactoryBean.getObject();
	}
}

package com.app.demo.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * redis保存session，解决分布式session一致性问题
 * 需要所有实体类实现Serializable接口
 * 注意：1.redis需要时2.8以上版本，否则会报错
 *      2.本类放在启动类能扫描到的地方
 * @author yuzhiyou
 *
 */
@Configuration
@EnableRedisHttpSession  
public class RedisSessionConfig {

}

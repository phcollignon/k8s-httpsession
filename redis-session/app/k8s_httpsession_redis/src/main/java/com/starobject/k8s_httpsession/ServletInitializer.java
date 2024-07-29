package com.starobject.k8s_httpsession;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;



@EnableRedisHttpSession 
public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(K8sHttpsessionApplication.class);
	}

	@Bean
	public LettuceConnectionFactory connectionFactory() {
		return new LettuceConnectionFactory(); 
	}

	@Bean
    public HttpSessionIdResolver httpSessionIdResolver() {
        return CookieHttpSessionIdResolver.withDefaults();
    }
}

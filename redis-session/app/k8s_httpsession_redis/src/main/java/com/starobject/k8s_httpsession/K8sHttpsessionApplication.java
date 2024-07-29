package com.starobject.k8s_httpsession;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;


@SpringBootApplication
public class K8sHttpsessionApplication {

	public static void main(String[] args) {
		SpringApplication.run(K8sHttpsessionApplication.class, args);
	}

}

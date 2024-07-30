package com.starobject.k8s_httpsession;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping;

@SpringBootApplication
public class K8sHttpsessionApplication {

	public static void main(String[] args) {
		SpringApplication.run(K8sHttpsessionApplication.class, args);
	}

	@Bean
	@Primary
	RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory rcf) {

		RedisTemplate<String, Object> template = new RedisTemplate<>();
		template.setConnectionFactory(rcf);
		template.setKeySerializer(new StringRedisSerializer());
		template.setValueSerializer(new JsonRedisSerializer());

		return template;
	}

	static class JsonRedisSerializer implements RedisSerializer<Object> {

		private final ObjectMapper om;

		public JsonRedisSerializer() {
			this.om = new ObjectMapper().enableDefaultTyping(DefaultTyping.NON_FINAL, As.PROPERTY);
		}

		@Override
		public byte[] serialize(Object t) throws SerializationException {
			try {
				return om.writeValueAsBytes(t);
			} catch (JsonProcessingException e) {
				throw new SerializationException(e.getMessage(), e);
			}
		}

		@Override
		public Object deserialize(byte[] bytes) throws SerializationException {

			if (bytes == null) {
				return null;
			}

			try {
				return om.readValue(bytes, Object.class);
			} catch (Exception e) {
				throw new SerializationException(e.getMessage(), e);
			}
		}
	}

}

package com.lecuong.sourcebase.config;

import com.lecuong.sourcebase.service.redis.MessagePublisher;
import com.lecuong.sourcebase.service.redis.MessagePublisherImpl;
import com.lecuong.sourcebase.service.redis.MessageSubscriber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.GenericToStringSerializer;

@Configuration
public class RedisConfig {
   private final Logger logger = LoggerFactory.getLogger(RedisConfig.class);

   @Value("${spring.redis.host}")
   private String redisHost;

   @Value("${spring.redis.port}")
   private Integer redisPort;

   @Bean
   JedisConnectionFactory jedisConnectionFactory() {
       RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(redisHost, redisPort);
       return new JedisConnectionFactory(redisStandaloneConfiguration);
   }

   @Bean(name = "redisTemplateCustom")
   public RedisTemplate<String, Object> redisTemplateCustom() {
       final RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
       template.setConnectionFactory(jedisConnectionFactory());
       template.setValueSerializer(new GenericToStringSerializer<Object>(Object.class));
       return template;
   }

   @Bean
   MessageListenerAdapter messageListener() {
       return new MessageListenerAdapter(new MessageSubscriber());
   }

   @Bean
   RedisMessageListenerContainer redisContainer() {
       final RedisMessageListenerContainer container = new RedisMessageListenerContainer();
       container.setConnectionFactory(jedisConnectionFactory());
       container.addMessageListener(messageListener(), topic());
       return container;
   }

   @Bean
   MessagePublisher redisPublisher() {
       return new MessagePublisherImpl(redisTemplateCustom(), topic());
   }

   @Bean
   ChannelTopic topic() {
       return new ChannelTopic("pubsub:queue");
   }
}

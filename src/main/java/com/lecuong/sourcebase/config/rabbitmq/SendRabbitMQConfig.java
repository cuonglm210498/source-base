package com.lecuong.sourcebase.config.rabbitmq;

import com.lecuong.sourcebase.constant.Constant;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author  CuongLM
 * @created 09/09/2023
 * @project source-base
 */

@Configuration
public class SendRabbitMQConfig {

    @Bean
    public Queue notificationQueue() {
        return new Queue(Constant.RabbitMQQueue.NOTIFICATION_QUEUE_NAME, false);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}

package com.lecuong.sourcebase.common;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.stereotype.Component;

/**
 * @author CuongLM18
 * @created 11/04/2023 - 1:45 PM
 * @project source-base
 */
@Getter
@Setter
@Component
@PropertySources({@PropertySource(value = "classpath:kafka-config.properties")})
public class CommonKafkaProperty {

    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;

    @Value("${spring.kafka.bootstrap-servers}")
    private String boostrapServers;

    @Value("${spring.kafka.consumer.auto-offset-reset}")
    private String autoOffsetReset;

    @Value("${source.kafka.topic}")
    private String topic;

}

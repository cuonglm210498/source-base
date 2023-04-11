package com.lecuong.sourcebase.kafka.service;

import com.lecuong.sourcebase.common.CommonKafkaProperty;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * @author CuongLM18
 * @created 11/04/2023 - 10:41 AM
 * @project source-base
 */
@Data
@Service
public class MessageProducerServiceImpl implements MessageProducerService {

    private final CommonKafkaProperty commonProperty;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void send(String message) {
        kafkaTemplate.send(commonProperty.getTopic(), message);
    }

    @Override
    public void send(String topic, String message) {
        kafkaTemplate.send(topic, message);
    }
}

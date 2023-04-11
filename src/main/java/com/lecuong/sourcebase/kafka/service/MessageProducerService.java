package com.lecuong.sourcebase.kafka.service;

/**
 * @author CuongLM18
 * @created 11/04/2023 - 1:55 PM
 * @project source-base
 */
public interface MessageProducerService {

    void send(String message);

    void send(String topic, String message);
}

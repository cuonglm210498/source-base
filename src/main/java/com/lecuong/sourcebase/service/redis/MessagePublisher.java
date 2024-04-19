package com.lecuong.sourcebase.service.redis;

public interface MessagePublisher {

    void publish(final String message);

}
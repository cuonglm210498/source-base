package com.lecuong.sourcebase.service;

/**
 * @author CuongLM
 * @created 09/09/2023
 * @project source-base
 */
public interface MessageService {

    void sendNotification(String actionCode, String functionCode, String functionId, String functionName, String user);
}

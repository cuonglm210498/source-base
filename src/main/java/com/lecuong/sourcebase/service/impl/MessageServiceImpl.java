package com.lecuong.sourcebase.service.impl;

import com.lecuong.sourcebase.constant.Constant;
import com.lecuong.sourcebase.modal.request.rabbitmq.NotificationRequest;
import com.lecuong.sourcebase.service.MessageService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author CuongLM
 * @created 09/09/2023
 * @project source-base
 */
@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Override
    public void sendNotification(String actionCode, String functionCode, String functionId, String functionName, String user) {
        NotificationRequest request = new NotificationRequest();
        request.setAction(actionCode);
        request.setFuncCode(functionCode);
        request.setFuncId(functionId);
        request.setFuncName(functionName);
        request.setReceiver(user);
        rabbitTemplate.convertAndSend(Constant.RabbitMQQueue.NOTIFICATION_QUEUE_NAME, request);
    }
}

package com.lecuong.sourcebase.listener;

import com.lecuong.sourcebase.constant.Constant;
import com.lecuong.sourcebase.modal.request.rabbitmq.NotificationRequest;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author CuongLM
 * @created 09/09/2023
 * @project source-base
 */
@Component
public class MessageListener {

    @RabbitListener(queues = Constant.RabbitMQQueue.NOTIFICATION_QUEUE_NAME)
    public void handleNotificationMessage(NotificationRequest request) {
        System.out.println(request);
    }
}

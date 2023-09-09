package com.lecuong.sourcebase.controller.rabbitmq;

import com.lecuong.sourcebase.modal.response.BaseResponse;
import com.lecuong.sourcebase.service.MessageService;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author CuongLM
 * @created 09/09/2023
 * @project source-base
 */
@Data
@RestController
@RequestMapping("/rabbit-mq")
public class MessageController {

    private final MessageService messageService;

    @PostMapping("/send")
    public ResponseEntity<BaseResponse<Void>> sendMessage() {
        messageService.sendNotification("test", "done", "F01", "test", "CuongLM");
        return ResponseEntity.ok(BaseResponse.ofSuccess(null));
    }
}

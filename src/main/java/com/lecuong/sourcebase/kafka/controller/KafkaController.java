package com.lecuong.sourcebase.kafka.controller;

import com.lecuong.sourcebase.common.CommonKafkaProperty;
import com.lecuong.sourcebase.kafka.service.MessageProducerService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author CuongLM18
 * @created 11/04/2023 - 2:26 PM
 * @project source-base
 */
@Slf4j
@Data
@RestController
@RequestMapping("/kafka")
public class KafkaController {

    private final CommonKafkaProperty commonKafkaProperty;
    private final MessageProducerService messageProducerService;

    @PostMapping("/send")
    public ResponseEntity<String> sendMessage(@RequestParam String message) {
        messageProducerService.send(commonKafkaProperty.getTopic(), message);
        return ResponseEntity.ok("Message sent to Kafka");
    }

//    @KafkaListener(topics = "test", groupId = "group-id")
    public void listen(String message) {
        log.info("Received Log History in group - group-id: " + message);
    }
}

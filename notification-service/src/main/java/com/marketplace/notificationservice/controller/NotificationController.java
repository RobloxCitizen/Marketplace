package com.marketplace.notificationservice.controller;

import com.marketplace.notificationservice.model.NotificationMessage;
import com.marketplace.notificationservice.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private KafkaTemplate<String, NotificationMessage> kafkaTemplate;

    @Autowired
    private NotificationService notificationService;

    @PostMapping("/send")
    public ResponseEntity<Void> sendNotification(@RequestBody NotificationMessage notificationMessage) {
        kafkaTemplate.send("order-notifications", notificationMessage);
        return ResponseEntity.ok().build();
    }
}
package com.marketplace.notificationservice.service;

import com.marketplace.notificationservice.model.NotificationMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

    @KafkaListener(topics = "order-notifications", groupId = "notification-group")
    public void handleOrderNotification(NotificationMessage message) {
        logger.info("Отправка уведомления:");
        logger.info("Тип: {}", message.getType());
        logger.info("Получатель: {}", message.getRecipient());
        logger.info("Тема: {}", message.getSubject());
        logger.info("Содержание: {}", message.getContent());
        logger.info("ID заказа: {}", message.getOrderId());
    }
}
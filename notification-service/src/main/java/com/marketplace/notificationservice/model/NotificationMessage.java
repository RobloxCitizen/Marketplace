package com.marketplace.notificationservice.model;

import java.io.Serializable;

public class NotificationMessage implements Serializable {
    private String type;
    private String recipient;
    private String subject;
    private String content;
    private Long orderId;

    // Пустой конструктор
    public NotificationMessage() {
    }

    // Конструктор с параметрами
    public NotificationMessage(String type, String recipient, String subject, String content, Long orderId) {
        this.type = type;
        this.recipient = recipient;
        this.subject = subject;
        this.content = content;
        this.orderId = orderId;
    }

    // Геттеры и сеттеры
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return "NotificationMessage{" +
                "type='" + type + '\'' +
                ", recipient='" + recipient + '\'' +
                ", subject='" + subject + '\'' +
                ", content='" + content + '\'' +
                ", orderId=" + orderId +
                '}';
    }
}
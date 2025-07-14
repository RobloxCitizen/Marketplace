package com.marketplace.orderservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marketplace.orderservice.model.Order;
import com.marketplace.orderservice.model.OrderItem;
import com.marketplace.orderservice.repository.OrderRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final KafkaTemplate<String, NotificationMessage> kafkaTemplate;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public OrderService(OrderRepository orderRepository, KafkaTemplate<String, NotificationMessage> kafkaTemplate, RestTemplate restTemplate) {
        this.orderRepository = orderRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.restTemplate = restTemplate;
    }

    public Order createOrder(Order order) {
        if (order.getItems() != null) {
            for (OrderItem item : order.getItems()) {
                item.setOrder(order);
                String productServiceUrl = "http://product-service:8080/api/products/" + item.getProductId();
                try {
                    restTemplate.getForObject(productServiceUrl, Object.class);
                } catch (Exception e) {
                    throw new RuntimeException("Product with ID " + item.getProductId() + " not found");
                }
            }
        }
        Order savedOrder = orderRepository.save(order);
        sendOrderNotification(savedOrder);
        return savedOrder;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    private void sendOrderNotification(Order order) {
        try {
            NotificationMessage notification = new NotificationMessage();
            notification.setType("ORDER_CREATED");
            notification.setOrderId(order.getId());
            notification.setRecipient(String.valueOf(order.getUserId()));
            notification.setSubject("Новый заказ создан");
            notification.setContent("Ваш заказ #" + order.getId() + " на сумму " + order.getTotal() + " руб. создан");

            kafkaTemplate.send("order-notifications", notification);
        } catch (Exception e) {
            System.err.println("Ошибка отправки уведомления: " + e.getMessage());
        }
    }
}

class NotificationMessage {
    private String type;
    private String recipient;
    private String subject;
    private String content;
    private Long orderId;

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getRecipient() { return recipient; }
    public void setRecipient(String recipient) { this.recipient = recipient; }
    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }
}
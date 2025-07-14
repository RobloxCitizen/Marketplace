package com.marketplace.orderservice.controller;

import com.marketplace.orderservice.model.Order;
import com.marketplace.orderservice.model.OrderItem;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class OrderControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private RestTemplate mockRestTemplate;

    @MockBean
    private KafkaTemplate<String, Object> mockKafkaTemplate;

    @Test
    @Transactional
    void testCreateOrder() {
        when(mockRestTemplate.getForObject("http://product-service:8080/api/products/1", Object.class))
                .thenReturn(new Object());

        Order order = new Order();
        order.setUserId(1L);
        order.setTotal(100.0);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("CREATED");

        OrderItem item = new OrderItem();
        item.setProductId(1L);
        item.setQuantity(2);
        item.setPrice(50.0);
        order.setItems(Collections.singletonList(item));

        ResponseEntity<Order> response = restTemplate.postForEntity("/api/orders", order, Order.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getId());
    }

    @Test
    @Transactional
    void testGetOrderById() {
        when(mockRestTemplate.getForObject("http://product-service:8080/api/products/1", Object.class))
                .thenReturn(new Object());

        Order order = new Order();
        order.setUserId(1L);
        order.setTotal(100.0);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("CREATED");

        OrderItem item = new OrderItem();
        item.setProductId(1L);
        item.setQuantity(2);
        item.setPrice(50.0);
        order.setItems(Collections.singletonList(item));

        ResponseEntity<Order> createResponse = restTemplate.postForEntity("/api/orders", order, Order.class);
        assertNotNull(createResponse.getBody());
        Long orderId = createResponse.getBody().getId();

        ResponseEntity<Order> response = restTemplate.getForEntity("/api/orders/" + orderId, Order.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(orderId, response.getBody().getId());
        assertEquals("CREATED", response.getBody().getStatus());
        assertEquals(1L, response.getBody().getUserId());
        assertEquals(100.0, response.getBody().getTotal());
    }
}
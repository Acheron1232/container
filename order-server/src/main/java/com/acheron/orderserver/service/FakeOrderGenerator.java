package com.acheron.orderserver.service;

import com.acheron.orderserver.entity.Order;
import com.acheron.orderserver.entity.OrderItem;
import com.acheron.orderserver.entity.Payment;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Service
@EnableScheduling
public class FakeOrderGenerator {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final List<String> uuids;

    public FakeOrderGenerator(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.uuids = new ArrayList<>();
        uuids.add(UUID.randomUUID().toString());
        uuids.add(UUID.randomUUID().toString());
        uuids.add(UUID.randomUUID().toString());
    }

    private final Random random = new Random();

    @Scheduled(fixedRate = 5000) // кожні 5 секунд
    public void produce() throws JsonProcessingException {
        Order order = generateFakeOrder();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        kafkaTemplate.send("orders", order.getId().toString(), objectMapper.writeValueAsString(order));
        System.out.println("Sent fake order: " + order.getId());
    }

    private Order generateFakeOrder() {
        UUID orderId = UUID.randomUUID();
        long userId = random.nextInt(1000) + 1;
        UUID facilityId = UUID.fromString(uuids.stream().findAny().get());
        String status = randomStatus();
        long totalPrice = random.nextInt(5000) + 100; // від 100 до 5100
        LocalDateTime createdAt = LocalDateTime.now();

        List<OrderItem> items = new ArrayList<>();
        int itemCount = random.nextInt(5) + 1; // 1-5 item
        for (int i = 0; i < itemCount; i++) {
            OrderItem item = OrderItem.builder()
                    .id(UUID.randomUUID())
                    .pizzaId(UUID.randomUUID())
                    .quantity(random.nextInt(3) + 1)
                    .price(BigDecimal.valueOf(random.nextInt(500) + 50))
                    .build();
            items.add(item);
        }

        Payment payment = Payment.builder()
                .id(UUID.randomUUID())
                .method(randomPaymentMethod())
                .status(randomPaymentStatus())
                .build();

        Order order = Order.builder()
                .id(orderId)
                .userId(userId)
                .facilityId(facilityId)
                .status(status)
                .totalPrice(totalPrice)
                .createdAt(createdAt)
                .items(items)
                .payment(payment)
                .build();

        // зв'язати об’єкти двосторонньо
        items.forEach(item -> item.setOrder(order));
        payment.setOrder(order);

        return order;
    }

    private String randomStatus() {
        String[] statuses = {"NEW"};
        return statuses[random.nextInt(statuses.length)];
    }

    private String randomPaymentMethod() {
        String[] methods = {"CASH", "CARD", "ONLINE"};
        return methods[random.nextInt(methods.length)];
    }

    private String randomPaymentStatus() {
        String[] statuses = {"PENDING", "PAID", "FAILED"};
        return statuses[random.nextInt(statuses.length)];
    }
}

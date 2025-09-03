package com.mykyda.kitchenserver.service;

import com.mykyda.kitchenserver.util.OrderParserUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumer {

    @Value("${facility.id:0}")
    private UUID facilityId;

    private final PizzaOrderService pizzaOrderService;

    @KafkaListener(topics = "orders", groupId = "my-group")
    public void listen(String message) {
        log.info("Received order {} from topic {}",message, "my-group");
        var order = OrderParserUtil.parse(message);
        if (order.getFacilityId().equals(facilityId)) {
            pizzaOrderService.createOrder(order);
        }
    }
}
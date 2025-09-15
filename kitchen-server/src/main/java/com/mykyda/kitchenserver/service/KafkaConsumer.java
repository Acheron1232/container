package com.mykyda.kitchenserver.service;

import com.mykyda.kitchenserver.util.OrderParserUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumer {

    private final OrderService orderService;

    private final KitchenService kitchenService;

    @KafkaListener(topics = "orders", groupId = "my-group")
    public void listen(String message) {
        log.info("Received order {} from topic {}", message, "my-group");
        var receivedOrderDTO = OrderParserUtil.parse(message);
        var order = orderService.createOrder(receivedOrderDTO);
        kitchenService.sendOrder(order, order.getFacilityId());
        log.info("order sent to topic orders");
    }
}
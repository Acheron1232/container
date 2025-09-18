package com.mykyda.deliveryserver.service;

import com.mykyda.deliveryserver.util.DeliveryOrderParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumer {

    private final DeliveryOrderService deliveryOrderService;

    @KafkaListener(topics = "delivery-orders", groupId = "my-group")
    public void consumeDeliveryOrder(String message) {
        log.info("Received delivery-order {}", message);
        var deliveryOrderDTO = DeliveryOrderParser.parse(message);
        var deliveryOrder = deliveryOrderService.createOrder(deliveryOrderDTO);
        System.out.println(deliveryOrder);
        log.info("Parsed DeliveryOrder: {}", deliveryOrderDTO);
    }
}
package com.mykyda.kitchenserver.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mykyda.kitchenserver.dto.OrderDTO;
import com.mykyda.kitchenserver.exception.OrderParserException;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendDeliveryOrder(OrderDTO order) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        try {
            kafkaTemplate.send("delivery-orders", order.getId().toString(), objectMapper.writeValueAsString(order));
        } catch (JsonProcessingException e) {
            throw new OrderParserException(e.getMessage());
        }
    }
}

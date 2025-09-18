package com.mykyda.deliveryserver.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mykyda.deliveryserver.database.enums.OrderStatus;
import com.mykyda.deliveryserver.database.enums.Type;
import com.mykyda.deliveryserver.database.repository.DeliveryOrderRepository;
import com.mykyda.deliveryserver.database.entity.DeliveryOrder;
import com.mykyda.deliveryserver.dto.DeliveryOrderDTO;
import com.mykyda.deliveryserver.exception.DatabaseException;
import com.mykyda.deliveryserver.exception.DeliveryOrderParserException;
import com.mykyda.deliveryserver.exception.EntityConflictException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;


@Service
@RequiredArgsConstructor
@Slf4j
public class DeliveryOrderService {

    private final DeliveryOrderRepository deliveryOrderRepository;

    public DeliveryOrder createOrder(DeliveryOrderDTO deliveryOrderDTO) {
        var objectMapper =  new ObjectMapper();
        try {
            var order = deliveryOrderRepository.findById(deliveryOrderDTO.getId());
            if (order.isEmpty()) {
                var orderToSave = DeliveryOrder.builder()
                        .id(deliveryOrderDTO.getId())
                        .facilityId(deliveryOrderDTO.getFacilityId())
                        .status(OrderStatus.valueOf(deliveryOrderDTO.getStatus()))
                        .createdAt(Timestamp.from(Instant.ofEpochMilli(deliveryOrderDTO.getCreatedAt())))
                        .type(Type.valueOf(deliveryOrderDTO.getType()))
                        .pizzaOrders(objectMapper.writeValueAsString(deliveryOrderDTO.getPizzaOrders()))
                        .build();
                var savedOrder = deliveryOrderRepository.save(orderToSave);
                log.info("saved order: {}", savedOrder.getId());
                return savedOrder;
            } else {
                log.info("order {} already exists", deliveryOrderDTO.getId());
                throw new EntityConflictException("order already exists");
            }
        } catch (DataAccessException e) {
            throw new DatabaseException(e.getMessage());
        } catch (JsonProcessingException e) {
            throw new DeliveryOrderParserException(e.getMessage());
        }
    }
}

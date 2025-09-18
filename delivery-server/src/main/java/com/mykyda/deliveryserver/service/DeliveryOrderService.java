package com.mykyda.deliveryserver.service;

import com.mykyda.deliveryserver.database.entity.DeliveryOrder;
import com.mykyda.deliveryserver.database.enums.OrderStatus;
import com.mykyda.deliveryserver.database.enums.Type;
import com.mykyda.deliveryserver.database.repository.DeliveryOrderRepository;
import com.mykyda.deliveryserver.dto.DeliveryOrderDTO;
import com.mykyda.deliveryserver.dto.ParsedDeliveryOrderDTO;
import com.mykyda.deliveryserver.exception.DatabaseException;
import com.mykyda.deliveryserver.exception.EntityConflictException;
import com.mykyda.deliveryserver.util.PizzaOrderMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class DeliveryOrderService {

    private final DeliveryOrderRepository deliveryOrderRepository;

    @Transactional
    public DeliveryOrder createOrder(ParsedDeliveryOrderDTO deliveryOrderDTO) {
        try {
            var order = deliveryOrderRepository.findById(deliveryOrderDTO.getId());
            if (order.isEmpty()) {
                var orderToSave = DeliveryOrder.builder()
                        .id(deliveryOrderDTO.getId())
                        .facilityId(deliveryOrderDTO.getFacilityId())
                        .status(OrderStatus.valueOf(deliveryOrderDTO.getStatus()))
                        .createdAt(Timestamp.from(Instant.ofEpochMilli(deliveryOrderDTO.getCreatedAt())))
                        .type(Type.valueOf(deliveryOrderDTO.getType()))
                        .pizzaOrders(PizzaOrderMapper.toJson(deliveryOrderDTO.getPizzaOrders()))
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
        }
    }

    @Transactional
    public List<DeliveryOrderDTO> getDeliveryOrders() {
        try {
            var deliveryOrders = deliveryOrderRepository.findAll().stream().map(DeliveryOrderDTO::of).toList();
            log.info("got delivery orders with ids: {}", deliveryOrders.stream().map(DeliveryOrderDTO::getId).toList());
            return deliveryOrders;
        } catch (DataAccessException e) {
            throw new DatabaseException(e.getMessage());
        }
    }
}

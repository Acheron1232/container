package com.mykyda.kitchenserver.service;

import com.mykyda.kitchenserver.database.entity.Order;
import com.mykyda.kitchenserver.database.entity.PizzaOrder;
import com.mykyda.kitchenserver.database.enums.OrderStatus;
import com.mykyda.kitchenserver.database.enums.PizzaStatus;
import com.mykyda.kitchenserver.database.repository.OrderRepository;
import com.mykyda.kitchenserver.dto.OrderDTO;
import com.mykyda.kitchenserver.dto.PizzaOrderDTO;
import com.mykyda.kitchenserver.dto.ReceivedOrderDTO;
import com.mykyda.kitchenserver.exception.DatabaseException;
import com.mykyda.kitchenserver.exception.EntityConflictException;
import com.mykyda.kitchenserver.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;

    private final KafkaProducer kafkaProducer;

    @Transactional
    public List<OrderDTO> getReceivedOrdersByFacilityId(UUID facilityId) {
        try {
            var orders = orderRepository.getOrdersByStatusNotAndFacilityIdOrderByCreatedAtAsc(OrderStatus.DELIVERED, facilityId);
            log.info("orders for facilityId {} found", facilityId);
            return orders.stream().map(OrderDTO::of).collect(Collectors.toList());
        } catch (DataAccessException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Transactional
    public OrderDTO createOrder(ReceivedOrderDTO order) {
        try {
            var orderCheck = orderRepository.findById(order.getId());
            if (orderCheck.isPresent()) {
                throw new EntityConflictException("Order already exists");
            }
            var orderToSave = Order.builder()
                    .id(order.getId())
                    .facilityId(order.getFacilityId())
                    .status(order.getStatus())
                    .createdAt(order.getCreatedAt())
                    .type(order.getType())
                    .build();
            var pizzaOrders = new ArrayList<PizzaOrder>();
            order.getPizzaOrders().forEach((id, quantity) -> pizzaOrders.add(PizzaOrder.builder()
                    .pizzaTemplateId(id)
                    .order(orderToSave)
                    .quantityOrdered(quantity)
                    .build()));
            orderToSave.setPizzaOrders(pizzaOrders);

            var savedOrder = OrderDTO.of(orderRepository.save(orderToSave));
            log.info("order saved {} with pizza orders {}", savedOrder.getId(), savedOrder.getPizzaOrders().stream().map(PizzaOrderDTO::getId).collect(Collectors.toList()));
            return savedOrder;
        } catch (DataAccessException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Transactional
    public void checkStatus(List<PizzaOrder> pizzaOrders) {
        var distinctOrderIds = pizzaOrders.stream().map(PizzaOrder::getOrder).map(Order::getId).distinct().toList();
        distinctOrderIds.forEach(orderId -> {
            try {
                var orderOptional = orderRepository.findById(orderId);
                if (orderOptional.isEmpty()) {
                    throw new EntityNotFoundException("Order with if " + orderId + " not found");
                }
                var orderToSave = orderOptional.get();
                var pizzaOrdersFromOrder = orderToSave.getPizzaOrders();
                var allReady = pizzaOrdersFromOrder.stream().allMatch(pizzaOrder -> pizzaOrder.getStatus().equals(PizzaStatus.READY));
                if (allReady) {
                    orderToSave.setStatus(OrderStatus.READY);
                    var savedOrder = orderRepository.save(orderToSave);
                    log.info("order saved {} with status {}", savedOrder.getId(), savedOrder.getStatus());
                    kafkaProducer.sendDeliveryOrder(OrderDTO.of(savedOrder));
                    log.info("order sent to delivery-orders kafka topic");
                }
            } catch (DataAccessException e) {
                throw new DatabaseException(e.getMessage());
            }
        });
    }
}

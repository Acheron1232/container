package com.mykyda.kitchenserver.service;

import com.mykyda.kitchenserver.dto.OrderDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class KitchenService {

    private final SimpMessagingTemplate messagingTemplate;

    private final PizzaOrderService pizzaOrderService;

    private final OrderService orderService;

    public void sendOrder(OrderDTO order, UUID facilityId) {
        messagingTemplate.convertAndSend("/topic/orders" + facilityId, order);
        log.info("Order message has been sent to facility {}", facilityId);
    }

    @Transactional
    public void setReadyQuantities(Map<UUID, Integer> pizzaOrdersReady) {
        if (!pizzaOrdersReady.isEmpty()) {
            var pizzaOrders = pizzaOrderService.setReadyQuantity(pizzaOrdersReady);
            orderService.checkStatus(pizzaOrders);
        }
    }
}

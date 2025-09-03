package com.mykyda.kitchenserver.service;

import com.mykyda.kitchenserver.dto.OrderDTO;
import com.mykyda.kitchenserver.database.entity.PizzaOrder;
import com.mykyda.kitchenserver.database.entity.PizzaStatus;
import com.mykyda.kitchenserver.database.repository.PizzaOrderRepository;
import com.mykyda.kitchenserver.dto.PizzaOrderDTO;
import com.mykyda.kitchenserver.exception.DatabaseException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class PizzaOrderService {

    private final PizzaOrderRepository pizzaOrderRepository;

    @Transactional
    public void createOrder(OrderDTO orderDTO) {
        var pizzaOrder = createPizzaOrdersFromOrderDTO(orderDTO);
        try {
            pizzaOrderRepository.saveAll(pizzaOrder);
            log.info("PizzaOrder has been saved successfully from order with id: {}", orderDTO.getId());
        } catch (DataAccessException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    private List<PizzaOrder> createPizzaOrdersFromOrderDTO(OrderDTO orderDTO) {
        var orders = new ArrayList<PizzaOrder>();
        orderDTO.getItems().forEach((key, value) -> {
            for (int i = 0; i < value; i++) {
                orders.add(PizzaOrder.builder()
                        .pizzaTemplateId(key)
                        .orderId(orderDTO.getId())
                        .createdAt(orderDTO.getCreatedAt())
                        .build());
            }
        });
        return orders;
    }

    @Transactional
    public List<PizzaOrderDTO> getOrderedPizzas() {
        try {
            var pizzas = pizzaOrderRepository.findAllByStatusOrderByCreatedAtAsc(PizzaStatus.ORDERED);
            log.info("ordered pizzas successfully acquired!");
            return pizzas.stream().map(PizzaOrderDTO::of).collect(Collectors.toList());
        } catch (DataAccessException e) {
            throw new DatabaseException(e.getMessage());
        }
    }
}

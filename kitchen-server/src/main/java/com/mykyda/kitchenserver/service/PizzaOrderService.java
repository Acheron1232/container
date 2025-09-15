package com.mykyda.kitchenserver.service;

import com.mykyda.kitchenserver.database.entity.PizzaOrder;
import com.mykyda.kitchenserver.database.repository.PizzaOrderRepository;
import com.mykyda.kitchenserver.exception.DatabaseException;
import com.mykyda.kitchenserver.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class PizzaOrderService {

    private final PizzaOrderRepository pizzaOrderRepository;

    @Transactional
    public List<PizzaOrder> setReadyQuantity(Map<UUID, Integer> pizzaOrdersReady) {
        var pizzaOrdersToSave = new ArrayList<PizzaOrder>();
        try {
            pizzaOrdersReady.forEach((pizzaOrderId, readyQuantity) -> {
                var pizzaOrderOptional = pizzaOrderRepository.findById(pizzaOrderId);
                if (pizzaOrderOptional.isEmpty()) {
                    throw new EntityNotFoundException("Pizza Order with id " + pizzaOrderId + " not found");
                }
                var pizzaOrderToSave = pizzaOrderOptional.get();
                if (readyQuantity < 0) {
                    readyQuantity = 0;
                } else if (readyQuantity > pizzaOrderToSave.getQuantityOrdered()) {
                    readyQuantity = pizzaOrderToSave.getQuantityOrdered();
                }
                if (!readyQuantity.equals(pizzaOrderToSave.getQuantityReady())) {
                    pizzaOrderToSave.setQuantityReady(readyQuantity);
                    pizzaOrdersToSave.add(pizzaOrderToSave);
                }
            });
            var savedPizzaOrders = pizzaOrderRepository.saveAllAndFlush(pizzaOrdersToSave);
            log.info("Pizza Orders with ids {} patched successfully", savedPizzaOrders.stream().map(PizzaOrder::getId).toArray());
            return savedPizzaOrders;
        } catch (DataAccessException e) {
            throw new DatabaseException(e.getMessage());
        }
    }
}

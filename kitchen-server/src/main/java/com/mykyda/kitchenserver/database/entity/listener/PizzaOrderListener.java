package com.mykyda.kitchenserver.database.entity.listener;

import com.mykyda.kitchenserver.database.entity.PizzaOrder;
import com.mykyda.kitchenserver.database.enums.PizzaStatus;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

public class PizzaOrderListener {

    @PrePersist
    @PreUpdate
    public void validateStatus(PizzaOrder pizzaOrder) {
        if (pizzaOrder.getQuantityReady() != null
                && pizzaOrder.getQuantityOrdered() != null
                && pizzaOrder.getQuantityReady().equals(pizzaOrder.getQuantityOrdered())) {
            pizzaOrder.setStatus(PizzaStatus.READY);
        }
        if ((pizzaOrder.getQuantityReady() != null
                && pizzaOrder.getQuantityOrdered() != null
                && pizzaOrder.getQuantityReady() > 0
                && pizzaOrder.getQuantityReady() < pizzaOrder.getQuantityOrdered())) {
            pizzaOrder.setStatus(PizzaStatus.IN_PROGRESS);
        }
        if ((pizzaOrder.getQuantityReady() != null
                && pizzaOrder.getQuantityOrdered() != null
                && pizzaOrder.getQuantityReady() == 0
                && !pizzaOrder.getQuantityReady().equals(pizzaOrder.getQuantityOrdered()))) {
            pizzaOrder.setStatus(PizzaStatus.ORDERED);
        } else if ((pizzaOrder.getQuantityReady() != null
                && pizzaOrder.getQuantityOrdered() != null
                && pizzaOrder.getQuantityReady() == 0)) {
            pizzaOrder.setStatus(PizzaStatus.READY);
        }
    }
}

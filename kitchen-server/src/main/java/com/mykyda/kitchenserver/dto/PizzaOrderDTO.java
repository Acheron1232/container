package com.mykyda.kitchenserver.dto;

import com.mykyda.kitchenserver.database.entity.PizzaOrder;
import com.mykyda.kitchenserver.database.enums.PizzaStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class PizzaOrderDTO {

    private UUID id;

    private UUID pizzaTemplateId;

    private Integer quantityOrdered;

    private Integer quantityReady;

    private PizzaStatus status;

    public static PizzaOrderDTO of(PizzaOrder pizzaOrder) {
        return PizzaOrderDTO.builder()
                .id(pizzaOrder.getId())
                .pizzaTemplateId(pizzaOrder.getPizzaTemplateId())
                .quantityOrdered(pizzaOrder.getQuantityOrdered())
                .quantityReady(pizzaOrder.getQuantityReady())
                .status(pizzaOrder.getStatus())
                .build();
    }
}

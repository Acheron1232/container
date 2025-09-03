package com.mykyda.kitchenserver.dto;

import com.mykyda.kitchenserver.database.entity.PizzaOrder;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
@Builder
public class PizzaOrderDTO {

    private UUID id;

    private UUID pizzaTemplateId;

    private UUID orderId;

    private String status;

    private Timestamp createdAt;

    public static PizzaOrderDTO of(PizzaOrder pizzaOrder) {
        return PizzaOrderDTO.builder()
                .id(pizzaOrder.getId())
                .pizzaTemplateId(pizzaOrder.getPizzaTemplateId())
                .orderId(pizzaOrder.getOrderId())
                .status(pizzaOrder.getStatus().name())
                .createdAt(pizzaOrder.getCreatedAt())
                .build();
    }
}

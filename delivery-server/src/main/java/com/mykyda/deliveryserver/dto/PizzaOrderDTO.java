package com.mykyda.deliveryserver.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class PizzaOrderDTO {

    private UUID pizzaTemplateId;

    private Integer quantity;
}

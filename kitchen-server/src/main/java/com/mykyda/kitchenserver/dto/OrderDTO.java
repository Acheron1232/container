package com.mykyda.kitchenserver.dto;

import com.mykyda.kitchenserver.database.entity.Order;
import com.mykyda.kitchenserver.database.enums.OrderStatus;
import com.mykyda.kitchenserver.database.enums.Type;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
@Builder
public class OrderDTO {

    private UUID id;

    private UUID facilityId;

    private OrderStatus status;

    private Timestamp createdAt;

    private Type type;

    private List<PizzaOrderDTO> pizzaOrders;

    public static OrderDTO of(Order order) {
        return OrderDTO.builder()
                .id(order.getId())
                .facilityId(order.getFacilityId())
                .status(order.getStatus())
                .createdAt(order.getCreatedAt())
                .type(order.getType())
                .pizzaOrders(order.getPizzaOrders().stream().map(PizzaOrderDTO::of).collect(Collectors.toList()))
                .build();
    }
}
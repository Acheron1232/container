package com.mykyda.deliveryserver.dto;

import com.mykyda.deliveryserver.database.entity.DeliveryOrder;
import com.mykyda.deliveryserver.database.enums.OrderStatus;
import com.mykyda.deliveryserver.database.enums.Type;
import com.mykyda.deliveryserver.util.PizzaOrderMapper;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Builder
@ToString
@Getter
public class DeliveryOrderDTO {

    private UUID id;

    private UUID facilityId;

    private OrderStatus status;

    private Timestamp createdAt;

    private  Timestamp acquiredAt;

    private Type type;

    private List<PizzaOrderDTO> pizzaOrders;

    public static DeliveryOrderDTO of(DeliveryOrder deliveryOrder) {
        return DeliveryOrderDTO.builder()
                .id(deliveryOrder.getId())
                .facilityId(deliveryOrder.getFacilityId())
                .status(deliveryOrder.getStatus())
                .createdAt(deliveryOrder.getCreatedAt())
                .acquiredAt(deliveryOrder.getAcquiredAt())
                .type(deliveryOrder.getType())
                .pizzaOrders(PizzaOrderMapper.fromJson(deliveryOrder.getPizzaOrders()))
                .build();
    }
}

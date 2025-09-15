package com.mykyda.kitchenserver.dto;

import com.mykyda.kitchenserver.database.enums.OrderStatus;
import com.mykyda.kitchenserver.database.enums.Type;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@Builder
public class ReceivedOrderDTO {
    private UUID id;

    private UUID facilityId;

    private OrderStatus status;

    private Timestamp createdAt;

    private Type type;

    @Builder.Default
    private Map<UUID,Integer> pizzaOrders = new HashMap<>();
}

package com.mykyda.kitchenserver.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@ToString
public class OrderDTO {

    private UUID id;

    private UUID facilityId;

    private OrderStatus status;

    private Timestamp createdAt;

    private Map<UUID,Integer> items;

    public enum OrderStatus {NEW}
}
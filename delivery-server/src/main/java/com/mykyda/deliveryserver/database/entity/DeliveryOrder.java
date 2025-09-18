package com.mykyda.deliveryserver.database.entity;

import com.mykyda.deliveryserver.database.enums.OrderStatus;
import com.mykyda.deliveryserver.database.enums.Type;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "delivery_orders")
@Builder
@ToString
public class DeliveryOrder {

    @Id
    private UUID id;

    @Column(nullable = false)
    private UUID facilityId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(nullable = false)
    private Timestamp createdAt;

    @Column(nullable = false)
    @Builder.Default
    private Timestamp acquiredAt =  Timestamp.from(Instant.now());

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Type type;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String pizzaOrders;
}

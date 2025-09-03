package com.mykyda.kitchenserver.database.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name= "pizza_order")
@Builder
public class PizzaOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private UUID pizzaTemplateId;

    private UUID orderId;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private PizzaStatus status = PizzaStatus.ORDERED;

    private Timestamp createdAt;
}

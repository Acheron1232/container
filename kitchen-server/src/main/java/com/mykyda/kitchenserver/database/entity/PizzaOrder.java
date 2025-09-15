package com.mykyda.kitchenserver.database.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.mykyda.kitchenserver.database.entity.listener.PizzaOrderListener;
import com.mykyda.kitchenserver.database.enums.PizzaStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(PizzaOrderListener.class)
@Table(name = "pizza_order")
@Builder
@ToString
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
public class PizzaOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private UUID pizzaTemplateId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(nullable = false)
    private Integer quantityOrdered;

    @Builder.Default
    private Integer quantityReady = 0;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private PizzaStatus status = PizzaStatus.ORDERED;
}

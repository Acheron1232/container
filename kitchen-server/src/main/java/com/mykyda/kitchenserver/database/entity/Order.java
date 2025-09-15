package com.mykyda.kitchenserver.database.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.mykyda.kitchenserver.database.enums.OrderStatus;
import com.mykyda.kitchenserver.database.enums.Type;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
@Builder
@ToString
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
public class Order {

    @Id
    private UUID id;

    @Column(nullable = false)
    private UUID facilityId;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private Timestamp createdAt;

    @Enumerated(EnumType.STRING)
    private Type type;

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private List<PizzaOrder> pizzaOrders;
}

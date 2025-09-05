package com.acheron.orderserver.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "facility_id")
    private UUID facilityId;

    @Column(name = "table_id")
    private UUID tableId;

    @Column(nullable = false)
    private STATUS status;

    @Column(name = "total_price", nullable = false)
    private Float totalPrice;

    @Column(name = "type", nullable = false)
    private TYPE type;

    @Column(name = "created_at", nullable = false, updatable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime createdAt = LocalDateTime.now();
    @Column(name = "placed_at", nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime placedAt = LocalDateTime.now();


    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<OrderItem> items;

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private Payment payment;

    enum STATUS {
        INIT,
        PAYED,
    }
    enum TYPE {
        ORDER,
        RESTAURANT
    }
}
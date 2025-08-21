package com.acheron.pizzaserver.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "orders")
data class Order(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @OneToMany(mappedBy = "order", cascade = [CascadeType.ALL], orphanRemoval = true)
    var pizzas: MutableList<OrderPizza>? = null,

    @Column(name = "user_id")
    val userId: Long,
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    val orderTime: LocalDateTime,
    @Enumerated(EnumType.STRING)
    val status: OrderStatus,
)
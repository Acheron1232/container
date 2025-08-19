package com.acheron.pizzaserver.entity

import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime
import java.util.UUID

@Entity
data class Delivery(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID,

    val address: String,
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    val deliveryTime: LocalDateTime,
    @Enumerated(EnumType.STRING)
    val status: Status,
    val deliveryMan: Long,
    val receiver: Long
)


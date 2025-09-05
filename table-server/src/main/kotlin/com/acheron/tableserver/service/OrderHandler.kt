package com.acheron.tableserver.service

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class OrderHandler {

    @KafkaListener(topics = ["orders"], groupId = "table-server")
    fun onMessage(payload: String) {
        val mapper = ObjectMapper()
        mapper.findAndRegisterModules()

        try {
            val order: OrderDto = mapper.readValue(payload, OrderDto::class.java)
            println(order)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        println(payload)
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class OrderDto(
        val id: String,
        val userId: Long,
        val facilityId: String,
        val tableId: String,
        val status: String,
        val totalPrice: Float?,
        val createdAt: List<Int>,
        val items: List<OrderItemDto>,
        val payment: PaymentDto?
    )

    data class OrderItemDto(
        val id: String,
        val pizzaId: String,
        val quantity: Int
    )

    data class PaymentDto(
        val id: String,
        val method: String,
        val status: String
    )
}

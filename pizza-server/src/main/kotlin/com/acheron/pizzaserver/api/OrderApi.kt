package com.acheron.pizzaserver.api

import com.acheron.pizzaserver.entity.Order
import com.acheron.pizzaserver.entity.OrderStatus
import com.acheron.pizzaserver.entity.Pizza
import com.acheron.pizzaserver.service.OrderService
import com.acheron.pizzaserver.util.User
import com.acheron.pizzaserver.util.Util
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime
import java.util.UUID
import java.util.stream.Collectors

@RestController
@RequestMapping("/order")
class OrderApi(
    val orderService: OrderService
) {

    @PostMapping
    fun makeOrder(@RequestBody order: CreateOrderRequest): OrderService.OrderDto {
        val orderS = orderService.save(order)
        return orderService.toDto(orderS);
    }

    @GetMapping
    fun getOrders(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
        @RequestParam(defaultValue = "orderTime", name = "sort_by") sortBy: String,
        @RequestParam(defaultValue = "asc", name = "sort_dir") sortDir: String,
        @RequestParam(defaultValue = "all", name = "order_status") orderStatus: String,
    ): List<OrderService.OrderDto> {
        val sort = if (sortDir.equals("desc", ignoreCase = true)) {
            Sort.by(sortBy).descending()
        } else {
            Sort.by(sortBy).ascending()
        }

        val pageable = PageRequest.of(page, size, sort)

        val orderPage: Page<Order> = if (orderStatus.equals("all", ignoreCase = true)) {
            orderService.findAll(pageable)
        } else {
            orderService.findAllByStatus(OrderStatus.valueOf(orderStatus.uppercase()), pageable)
        }

        return orderPage.content.stream().map { orderService.toDto(it) }.collect(Collectors.toList())
    }

    data class CreateOrderRequest(
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        val orderTime: LocalDateTime,
        val pizzas: List<PizzaQuantity>
    )

    data class PizzaQuantity(
        val pizzaId: UUID,
        val quantity: Int
    )
}
//package com.acheron.pizzaserver.service
//
//import com.acheron.pizzaserver.api.OrderApi
//import com.acheron.pizzaserver.entity.Pizza
//import com.acheron.pizzaserver.repo.PizzaRepository
//import com.acheron.pizzaserver.util.User
//import com.acheron.pizzaserver.util.Util
//import jakarta.persistence.EntityManager
//import org.springframework.data.domain.Page
//import org.springframework.data.domain.PageRequest
//import org.springframework.stereotype.Service
//import java.time.LocalDateTime
//import java.util.UUID
//
//@Service
//class OrderService(
//    val orderRepository: OrderRepository,
//    val util: Util,
//    val entityManager: EntityManager,
//    val orderPizzaService: OrderPizzaService
//) {
//    fun save(orderDto: OrderApi.CreateOrderRequest): Order {
//
//        val order =
//            orderRepository.save(Order(null, null, util.getCurrentUser().id, orderDto.orderTime, OrderStatus.SCHEDULED))
//        val pizzas = orderDto.pizzas.map { pizza ->
//            val pizzaRef = entityManager.getReference(Pizza::class.java, pizza.pizzaId)
//            OrderPizza(null, order, pizzaRef, pizza.quantity)
//        }.toMutableList()
//        order.pizzas = pizzas
//
//        return orderRepository.save(order)
//    }
//
//    fun findAllByStatus(orderStatus: OrderStatus, pageable: PageRequest): Page<Order> {
//        val id = util.getCurrentUser().id
//        return orderRepository.findAllByUserIdAndStatus(id, orderStatus, pageable)
//    }
//
//    fun findAll(pageable: PageRequest): Page<Order> {
//        val id = util.getCurrentUser().id
//        return orderRepository.findAllByUserId(id, pageable)
//
//    }
//
//
//    fun toDto(order: Order): OrderDto = OrderDto(
//        id = order.id,
//        pizzas = order.pizzas?.map { OrderPizzaDto(it.pizza.id!!, it.quantity, it.pizza.price, it.pizza.name) } ?: emptyList(),
//        userId = order.userId,
//        orderTime = order.orderTime,
//        status = order.status.name
//    )
//
//    data class OrderPizzaDto(
//        val pizzaId: UUID,
//        val quantity: Int,
//        val price: Double,
//        val name: String
//    )
//
//    data class OrderDto(
//        val id: UUID?,
//        val pizzas: List<OrderPizzaDto>,
//        val userId: Long,
//        val orderTime: LocalDateTime,
//        val status: String
//    )
//
//}
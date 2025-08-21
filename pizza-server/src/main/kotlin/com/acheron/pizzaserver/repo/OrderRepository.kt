package com.acheron.pizzaserver.repo

import com.acheron.pizzaserver.entity.Order
import com.acheron.pizzaserver.entity.OrderStatus
import com.acheron.pizzaserver.entity.Pizza
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID
@Repository
interface OrderRepository : CrudRepository<Order, UUID> {
    fun findAll(pageable: Pageable): Page<Order>

    fun findAllByStatus(status: OrderStatus, pageable: Pageable): Page<Order>

    fun findAllByUserIdAndStatus(
        userId: Long,
        status: OrderStatus,
        pageable: Pageable
    ): Page<Order>

    fun findAllByUserId(userId: Long, pageable: Pageable): Page<Order>



}
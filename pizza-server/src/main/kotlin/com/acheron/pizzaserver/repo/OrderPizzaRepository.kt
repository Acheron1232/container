package com.acheron.pizzaserver.repo

import com.acheron.pizzaserver.entity.OrderPizza
import com.acheron.pizzaserver.entity.Pizza
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID
@Repository
interface OrderPizzaRepository : CrudRepository<OrderPizza, UUID> {

//    fun saveAll(orderPizzas: List<OrderPizza>): List<OrderPizza>

}
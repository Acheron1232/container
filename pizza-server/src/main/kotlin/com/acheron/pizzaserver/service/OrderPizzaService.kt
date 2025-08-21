package com.acheron.pizzaserver.service

import com.acheron.pizzaserver.entity.OrderPizza
import com.acheron.pizzaserver.repo.OrderPizzaRepository
import org.springframework.stereotype.Service

@Service
class OrderPizzaService(
    val orderPizzaRepository: OrderPizzaRepository
) {
//    fun saveAll(pizzas: List<OrderPizza>): List<OrderPizza> {
//        return orderPizzaRepository.saveAll(pizzas)
//    }
}
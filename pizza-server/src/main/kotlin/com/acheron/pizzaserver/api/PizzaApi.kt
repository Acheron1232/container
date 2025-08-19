package com.acheron.pizzaserver.api

import com.acheron.pizzaserver.entity.Pizza
import com.acheron.pizzaserver.repo.PizzaRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/pizza")
class PizzaApi(
    private val pizzaRepository: PizzaRepository
 ) {

    @GetMapping
    fun getPizzas(): List<Pizza> {
        return pizzaRepository.findAll().toList();
    }

    @GetMapping("/{type}")
    fun getPizzasByType(@PathVariable type: String): Pizza {
        return pizzaRepository.findByNameIgnoreCase(type);
    }
}
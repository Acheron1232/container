package com.acheron.pizzaserver.api

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/orders")
class OrderApi {

    @GetMapping("/orders")
    fun getOrders(){

    }
}
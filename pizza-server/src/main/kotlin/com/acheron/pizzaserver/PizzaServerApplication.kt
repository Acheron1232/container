package com.acheron.pizzaserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PizzaServerApplication

fun main(args: Array<String>) {
    runApplication<PizzaServerApplication>(*args)
}

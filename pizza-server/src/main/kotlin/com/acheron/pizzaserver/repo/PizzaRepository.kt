package com.acheron.pizzaserver.repo

import com.acheron.pizzaserver.entity.Pizza
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID
@Repository
interface PizzaRepository : CrudRepository<Pizza, UUID> {
    fun findByNameIgnoreCase(name: String): Pizza

    fun findAll(pageable: Pageable): Page<Pizza>
}
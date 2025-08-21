package com.acheron.pizzaserver.api

import com.acheron.pizzaserver.entity.Pizza
import com.acheron.pizzaserver.service.PizzaService
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
class PizzaApi(
    private val pizzaService: PizzaService
) {

    @GetMapping
    fun getPizzas(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
        @RequestParam(defaultValue = "name", name = "sort_by") sortBy: String,
        @RequestParam(defaultValue = "asc", name = "sort_dir") sortDir: String
    ): List<Pizza> {
        val sort = if (sortDir.equals("desc", ignoreCase = true)) {
            Sort.by(sortBy).descending()
        } else {
            Sort.by(sortBy).ascending()
        }
        val pageable = PageRequest.of(page, size, sort)
        val pizzaPage = pizzaService.findAll(pageable)
        return pizzaPage.content
    }

    @GetMapping("/{name}")
    fun getPizzasByType(@PathVariable name: String): Pizza? {
        return pizzaService.findByNameIgnoreCase(name);
    }

    @PostMapping
    fun save(
        @RequestParam(name = "images") images: List<MultipartFile>, @RequestParam(name = "data") data: String
    ): Pizza {
        val pizzaDto = ObjectMapper().readValue(data, Pizza::class.java)
        return pizzaService.save(pizzaDto, images);
    }
}
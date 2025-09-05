package com.acheron.pizzaserver.api

import com.acheron.pizzaserver.entity.Facility
import com.acheron.pizzaserver.entity.Pizza
import com.acheron.pizzaserver.service.FacilityService
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/facility")
class FacilityApi(
    private val facilityService: FacilityService
) {

    @GetMapping
    fun findAll(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
        @RequestParam(defaultValue = "name", name = "sort_by") sortBy: String,
        @RequestParam(defaultValue = "asc", name = "sort_dir") sortDir: String
    ): List<Facility> {
        val pageable = UtilApiComponent.pageable(page, size, sortBy, sortDir)
        val pizzaPage = facilityService.findAll(pageable)
        return pizzaPage.content
    }

    @GetMapping("/{name}")
    fun getFacilityByName(@PathVariable name: String): Facility? {
        return facilityService.findByName(name);
    }
}
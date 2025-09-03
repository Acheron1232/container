package com.acheron.pizzaserver.repo

import com.acheron.pizzaserver.entity.Facility
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID
@Repository
interface FacilityRepository : CrudRepository<Facility, UUID> {
    fun findAll(pageable: Pageable): Page<Facility>
    fun findFacilityByName(name: String): Facility?
}
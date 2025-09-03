package com.acheron.pizzaserver.service

import com.acheron.pizzaserver.entity.Facility
import com.acheron.pizzaserver.repo.FacilityRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class FacilityService(
    private val facilityRepository: FacilityRepository
) {

    fun findAll(pageable: Pageable): Page<Facility> {
        return facilityRepository.findAll(pageable)
    }

    fun findByName(name: String): Facility? {
        return facilityRepository.findFacilityByName(name);
    }


}
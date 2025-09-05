package com.acheron.pizzaserver.api

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort

class UtilApiComponent {
    companion object {
        fun pageable(page: Int, size: Int, sortBy: String, sortDir: String): PageRequest {
            val sort = if (sortDir.equals("desc", ignoreCase = true)) {
                Sort.by(sortBy).descending()
            } else {
                Sort.by(sortBy).ascending()
            }
            return PageRequest.of(page, size, sort)
        }
    }
}
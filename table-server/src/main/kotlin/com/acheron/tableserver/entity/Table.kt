package com.acheron.tableserver.entity

import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.util.UUID

@Entity
@jakarta.persistence.Table(name = "restaurant_table")
data class Table(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,
    val capacity: Int,
    val facilityId: UUID,
    @Enumerated(EnumType.STRING)
    val status: STATUS,
){
    enum class STATUS{
        AVAILABLE, OCCUPIED,CANCELLED,RESERVED
    }
}
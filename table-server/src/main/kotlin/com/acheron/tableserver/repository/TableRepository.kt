package com.acheron.tableserver.repository

import com.acheron.tableserver.entity.Table
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface TableRepository : JpaRepository<Table, Long> {
//    fun findAll():List<Table>;

}
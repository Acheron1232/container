package com.acheron.tableserver.service

import com.acheron.tableserver.entity.Table
import com.acheron.tableserver.repository.TableRepository
import org.springframework.stereotype.Service

@Service
class TableService(
    private val repository: TableRepository
) {
    fun findAll(): List<Table> {
        return repository.findAll();
    }
}
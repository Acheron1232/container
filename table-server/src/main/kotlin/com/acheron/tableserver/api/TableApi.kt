package com.acheron.tableserver.api

import com.acheron.tableserver.entity.Table
import com.acheron.tableserver.service.TableService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TableApi(
    private val service: TableService,
) {

    @GetMapping
    fun all(): List<Table> {
        return service.findAll()
    }
}
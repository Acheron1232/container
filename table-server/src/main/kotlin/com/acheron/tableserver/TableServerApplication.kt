package com.acheron.tableserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TableServerApplication

fun main(args: Array<String>) {
	runApplication<TableServerApplication>(*args)
}

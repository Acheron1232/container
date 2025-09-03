package com.acheron.pizzaserver.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.JoinTable
import jakarta.persistence.ManyToMany
import java.util.UUID

@Entity
data class Pizza(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,
    val name: String,
    val description: String? = null,
    var images: List<String>? = ArrayList(),
    val price: Double,
    @ManyToMany
    @JoinTable(
        name = "pizza_ingredient",
        joinColumns = [JoinColumn(name = "pizza_id")],
        inverseJoinColumns = [JoinColumn(name = "ingredient_id")]
    )
    var ingredients: MutableSet<Ingredient> = mutableSetOf()
)
package com.acheron.pizzaserver.repo

import com.acheron.pizzaserver.entity.Ingredient
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID
@Repository
interface IngredientRepository : CrudRepository<Ingredient, UUID> {

}
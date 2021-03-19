package com.example.pickrecipe.db.model

import androidx.room.Embedded
import androidx.room.Relation

data class RecipesWithIngredients (
    @Embedded val recipe : RecipeEntity,
    @Relation(
        parentColumn = "recipeId",
        entityColumn = "ingredientId"
    )
    val ingredient : IngredientEntity
)
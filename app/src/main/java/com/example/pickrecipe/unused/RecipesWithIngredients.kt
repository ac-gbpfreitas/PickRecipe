package com.example.pickrecipe.unused

import androidx.room.Embedded
import androidx.room.Relation
import com.example.pickrecipe.db.model.RecipeEntity

data class RecipesWithIngredients (
    @Embedded val recipe : RecipeEntity,
    @Relation(
        parentColumn = "recipeId",
        entityColumn = "ingredientId"
    )
    val ingredient : IngredientEntity
)
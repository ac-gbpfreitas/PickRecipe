package com.example.pickrecipe.unused

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface RecipeWithIngredientDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRecipesWithIngredients(newRecipesWithIngredients : RecipesWithIngredients)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(newRecipesWithIngredientsList : List<RecipesWithIngredients>)

    @Query("SELECT * FROM recipe")
    fun getAllRecipesWithIngredients() : LiveData<List<RecipesWithIngredients>>

    @Query("SELECT * FROM recipe WHERE recipeId = :id")
    suspend fun getRecipesWithIngredients(id : String) : RecipesWithIngredients

    @Query("DELETE FROM recipe")
    suspend fun deleteAllRecipesWithIngredients()

    @Query("DELETE FROM recipe WHERE recipeId = :id")
    fun deleteRecipesWithIngredients(id : String);

    @Update
    suspend fun updateRecipesWithIngredients(recipesWithIngredients : RecipesWithIngredients)
}
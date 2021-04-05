package com.example.pickrecipe.unused

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface IngredientDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addIngredientEntity(newIngredientEntity : IngredientEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(newIngredientEntityList : List<IngredientEntity>)

    @Query("SELECT * FROM ingredient")
    fun getAllIngredients() : LiveData<List<IngredientEntity>>

    @Query("SELECT * FROM ingredient WHERE ingredientId = :id")
    suspend fun getIngredient(id : Int) : IngredientEntity

    @Query("SELECT * FROM ingredient WHERE recipeId = :id")
    fun getIngredientByRecipe(id : String) : List<IngredientEntity>;

    @Query("DELETE FROM ingredient")
    suspend fun deleteAllIngredients()

    @Query("DELETE FROM ingredient WHERE ingredientId = :id")
    fun deleteIngredient(id : Int);

    @Update
    suspend fun updateIngredientEntity(ingredientEntity : IngredientEntity)
}
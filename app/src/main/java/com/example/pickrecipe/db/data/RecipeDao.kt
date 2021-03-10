package com.example.pickrecipe.db.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.pickrecipe.db.model.RecipeEntity

@Dao
interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRecipeEntity(newRecipeEntity : RecipeEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(newRecipeEntityList : List<RecipeEntity>)

    @Query("SELECT * FROM recipe")
    fun getAllRecipes() : LiveData<List<RecipeEntity>>

    @Query("SELECT * FROM recipe WHERE recipeId = :id")
    suspend fun getRecipe(id : String) : RecipeEntity

    @Query("DELETE FROM recipe")
    suspend fun deleteAllRecipes()

    @Query("DELETE FROM recipe WHERE recipeId = :id")
    fun deleteRecipe(id : String);

    @Update
    suspend fun updateRecipeEntity(RecipeEntity : RecipeEntity)

}
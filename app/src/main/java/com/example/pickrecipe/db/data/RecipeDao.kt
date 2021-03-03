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

    @Query("SELECT * FROM recipe WHERE id = :id")
    suspend fun getRecipe(id : Int) : RecipeEntity

    @Query("DELETE FROM recipe")
    suspend fun deleteAllRecipes()

    @Query("DELETE FROM recipe WHERE id = :id")
    fun deleteRecipe(id : Int);

    @Update
    suspend fun updateRecipeEntity(RecipeEntity : RecipeEntity)

}
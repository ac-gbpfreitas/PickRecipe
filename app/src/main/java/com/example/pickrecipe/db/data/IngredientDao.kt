package com.example.pickrecipe.db.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.pickrecipe.db.model.IngredientEntity


@Dao
interface IngredientDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addIngredientEntity(newIngredientEntity : IngredientEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(newIngredientEntityList : List<IngredientEntity>)

    @Query("SELECT * FROM Ingredient")
    fun getAllIngredients() : LiveData<List<IngredientEntity>>

    @Query("SELECT * FROM Ingredient WHERE id = :id")
    suspend fun getIngredient(id : Int) : IngredientEntity

    @Query("DELETE FROM ingredient")
    suspend fun deleteAllIngredients()

    @Query("DELETE FROM Ingredient WHERE id = :id")
    fun deleteIngredient(id : Int);

    @Update
    suspend fun updateIngredientEntity(IngredientEntity : IngredientEntity)
}
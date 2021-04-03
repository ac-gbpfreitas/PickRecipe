package com.example.pickrecipe.unused

import androidx.lifecycle.LiveData

class IngredientRepository(private val ingredientDao: IngredientDao) {

    val readAllData : LiveData<List<IngredientEntity>> = ingredientDao.getAllIngredients();

    suspend fun addIngredient(newIngredient : IngredientEntity){
        ingredientDao.addIngredientEntity(newIngredient);
    }

    suspend fun updateIngredient(newIngredient : IngredientEntity){
        ingredientDao.updateIngredientEntity(newIngredient);
    }

    suspend fun getIngredient(id : Int) : IngredientEntity {
        return ingredientDao.getIngredient(id);
    }

    fun deleteIngredient(id : Int){
        ingredientDao.deleteIngredient(id);
    }

    suspend fun deleteAllIngredients(){
        ingredientDao.deleteAllIngredients();
    }

    fun getIngredientsByRecipe(id : String) : List<IngredientEntity>{
        return ingredientDao.getIngredientByRecipe(id);
    }

    suspend fun addAllIngredients(ingredientList : List<IngredientEntity>){
        ingredientDao.insertAll(ingredientList);
    }
}
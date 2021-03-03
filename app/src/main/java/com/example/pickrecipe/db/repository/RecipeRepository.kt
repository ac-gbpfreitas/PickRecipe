package com.example.pickrecipe.db.repository

import androidx.lifecycle.LiveData
import com.example.pickrecipe.db.data.RecipeDao
import com.example.pickrecipe.db.model.RecipeEntity

class RecipeRepository(private val recipeDao: RecipeDao) {

    val readAllData : LiveData<List<RecipeEntity>> = recipeDao.getAllRecipes();

    suspend fun addRecipe(newRecipe : RecipeEntity){
        recipeDao.addRecipeEntity(newRecipe);
    }

    suspend fun updateRecipe(recipe : RecipeEntity){
        recipeDao.updateRecipeEntity(recipe);
    }

    fun deleteRecipe(id : Int){
        recipeDao.deleteRecipe(id);
    }

    suspend fun deleteAllRecipes(){
        recipeDao.deleteAllRecipes();
    }

    suspend fun addAllRecipes(recipeList : List<RecipeEntity>){
        recipeDao.insertAll(recipeList);
    }
}
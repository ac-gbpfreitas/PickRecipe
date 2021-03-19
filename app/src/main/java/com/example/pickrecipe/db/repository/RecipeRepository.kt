package com.example.pickrecipe.db.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.pickrecipe.db.data.RecipeDao
import com.example.pickrecipe.db.model.RecipeEntity

class RecipeRepository(private val recipeDao: RecipeDao) {

    val readAllData : LiveData<List<RecipeEntity>> = recipeDao.getAllRecipes();

    suspend fun addRecipe(newRecipe : RecipeEntity){
        recipeDao.addRecipeEntity(newRecipe);
    }

    fun getAllRecipes() : LiveData<List<RecipeEntity>> {
        return recipeDao.getAllRecipes();
    }

    suspend fun getRecipe(id : String) : RecipeEntity{
        return recipeDao.getRecipe(id);
    }

    suspend fun updateRecipe(recipe : RecipeEntity){
        recipeDao.updateRecipeEntity(recipe);
    }

    fun deleteRecipe(id : String){
        recipeDao.deleteRecipe(id);
    }

    suspend fun deleteAllRecipes(){
        recipeDao.deleteAllRecipes();
    }

    suspend fun addAllRecipes(recipeList : List<RecipeEntity>){
        recipeDao.insertAll(recipeList);
    }
}
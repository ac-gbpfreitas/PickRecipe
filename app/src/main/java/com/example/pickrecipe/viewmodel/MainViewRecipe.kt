package com.example.pickrecipe.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.pickrecipe.db.data.IngredientDatabase
import com.example.pickrecipe.db.data.RecipeDatabase
import com.example.pickrecipe.db.model.IngredientEntity
import com.example.pickrecipe.db.model.RecipeEntity
import com.example.pickrecipe.db.repository.IngredientRepository
import com.example.pickrecipe.db.repository.RecipeRepository
import com.example.pickrecipe.json.RecipeJsonReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewRecipe (application: Application) : AndroidViewModel(application){

    val readAllRecipes : LiveData<List<RecipeEntity>>;
    private val repositoryRecipes : RecipeRepository;


    init{
        val recipeDao = RecipeDatabase.getDatabase(application)?.recipeDao();
        repositoryRecipes = RecipeRepository(recipeDao!!);
        readAllRecipes = repositoryRecipes.readAllData;
    }

    fun addRecipe(newRecipe : RecipeEntity){
        viewModelScope.launch(Dispatchers.IO){
            repositoryRecipes.addRecipe(newRecipe);
        }
    }

    fun deleteRecipe(id : Int){
        viewModelScope.launch(Dispatchers.IO) {
            repositoryRecipes.deleteRecipe(id);
        }
    }

    fun updateRecipe(newRecipe : RecipeEntity){
        viewModelScope.launch(Dispatchers.IO) {
            repositoryRecipes.updateRecipe(newRecipe);
        }
    }

    fun deleteAllRecipes(){
        viewModelScope.launch(Dispatchers.IO) {
            repositoryRecipes.deleteAllRecipes();
        }
    }

    fun addAllRecipes(filename : String){
        viewModelScope.launch(Dispatchers.IO) {
            if(repositoryRecipes.readAllData.value?.isEmpty() == true){
                var dataFromJson = RecipeJsonReader(getApplication(),filename).recipeEntities
                repositoryRecipes.addAllRecipes(dataFromJson);
            }
        }
    }
}
package com.example.pickrecipe.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.pickrecipe.db.data.RecipeIngredientDatabase
import com.example.pickrecipe.db.model.IngredientEntity
import com.example.pickrecipe.db.model.RecipeEntity
import com.example.pickrecipe.db.repository.IngredientRepository
import com.example.pickrecipe.db.repository.RecipeRepository
import com.example.pickrecipe.json.RecipeJsonReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecipeViewModel (application: Application) : AndroidViewModel(application){

    val readAllRecipes : LiveData<List<RecipeEntity>>;
    val readAllIngredients : LiveData<List<IngredientEntity>>;

    private val repositoryRecipes : RecipeRepository;
    private val repositoryIngredients : IngredientRepository;


    init{
        val recipeDao = RecipeIngredientDatabase.getDatabase(application)?.recipeDao();
        val ingredientDao = RecipeIngredientDatabase.getDatabase(application)?.ingredientDao();

        //val recipeDao = RecipeDatabase.getDatabase(application)?.recipeDao();
        repositoryRecipes = RecipeRepository(recipeDao!!);
        repositoryIngredients = IngredientRepository(ingredientDao!!)

        readAllRecipes = repositoryRecipes.readAllData;
        readAllIngredients = repositoryIngredients.readAllData;
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

    /*
    fun getAllRecipes() : LiveData<List<RecipeEntity>>{
        viewModelScope.launch(Dispatchers.IO) {
            repositoryRecipes.getAllRecipes();
        }
    }
    */

    fun addAllRecipes(){
        viewModelScope.launch(Dispatchers.IO) {
            if( repositoryRecipes.readAllData.value == null ||
                    repositoryRecipes.readAllData.value?.isEmpty() == true
            ){
                var recipeFromJson = RecipeJsonReader(getApplication(),"recipes.json").recipeEntities
                repositoryRecipes.addAllRecipes(recipeFromJson);
                var ingredientFromJson = RecipeJsonReader(getApplication(),"recipes.json").ingredientEntities
                repositoryIngredients.addAllIngredients(ingredientFromJson);
                //Log.d("DATABASE_INGREDIENT",repositoryIngredients.getIngredient(3).detail);
            }
        }
    }
}
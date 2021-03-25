package com.example.pickrecipe.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.pickrecipe.db.data.RecipeIngredientDatabase
import com.example.pickrecipe.db.model.IngredientEntity
import com.example.pickrecipe.db.model.RecipeEntity
import com.example.pickrecipe.db.repository.IngredientRepository
import com.example.pickrecipe.db.repository.RecipeRepository
import com.example.pickrecipe.json.RecipeJsonReader
import com.example.pickrecipe.json.RecipeJsonReaderTester
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecipeViewModel (application: Application) : AndroidViewModel(application){

    val readAllRecipes : LiveData<List<RecipeEntity>>;
    val readAllIngredients : LiveData<List<IngredientEntity>>;

    //var ingredientsFromRecipe : ArrayList<IngredientEntity> = arrayListOf();

    private val repositoryRecipes : RecipeRepository;
    private val repositoryIngredients : IngredientRepository;


    companion object{
        var ingredientsFromRecipe : ArrayList<IngredientEntity> = arrayListOf();
    }

    init{
        val recipeDao = RecipeIngredientDatabase.getDatabase(application)?.recipeDao();
        val ingredientDao = RecipeIngredientDatabase.getDatabase(application)?.ingredientDao();

        repositoryRecipes = RecipeRepository(recipeDao!!);
        repositoryIngredients = IngredientRepository(ingredientDao!!)

        readAllRecipes = repositoryRecipes.readAllData
        readAllIngredients = repositoryIngredients.readAllData
    }

    fun addRecipe(newRecipe : RecipeEntity){
        viewModelScope.launch(Dispatchers.IO){
            repositoryRecipes.addRecipe(newRecipe);
        }
    }

    fun deleteRecipe(id : String){
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

    fun getIngredientsByRecipeId(id : String) {
        viewModelScope.launch(Dispatchers.IO) {
            ingredientsFromRecipe = repositoryIngredients.getIngredientsByRecipe(id) as ArrayList<IngredientEntity>;
            Log.d("INGREDIENT",ingredientsFromRecipe.size.toString());
        }

    }

    fun getRecipeById(id: String){
        viewModelScope.launch(Dispatchers.IO) {
            repositoryRecipes.getRecipe(id)
        }
    }

    fun addAllRecipes(data : String){
        viewModelScope.launch(Dispatchers.IO) {
            if( repositoryRecipes.readAllData.value == null ||
                    repositoryRecipes.readAllData.value?.isEmpty() == true
            ){
                var recipeFromJson = RecipeJsonReaderTester(getApplication(),data).recipeEntities
                repositoryRecipes.addAllRecipes(recipeFromJson);
                /*
                var ingredientFromJson = RecipeJsonReader(getApplication(),"recipes.json").ingredientEntities
                repositoryIngredients.addAllIngredients(ingredientFromJson);
                */
            }
        }
    }
}
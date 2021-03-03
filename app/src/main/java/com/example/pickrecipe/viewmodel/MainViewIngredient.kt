package com.example.pickrecipe.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.pickrecipe.db.data.IngredientDatabase
import com.example.pickrecipe.db.model.IngredientEntity
import com.example.pickrecipe.db.repository.IngredientRepository
import com.example.pickrecipe.json.IngredientJsonReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewIngredient (application: Application) : AndroidViewModel(application) {

    private val repositoryIngredients : IngredientRepository;
    val readAllIngredients : LiveData<List<IngredientEntity>>;

    init{
        val ingredientDao = IngredientDatabase.getDatabase(application)?.ingredientDao();
        repositoryIngredients = IngredientRepository(ingredientDao!!);
        readAllIngredients = repositoryIngredients.readAllData;
    }

    fun addIngredient(newIngredient : IngredientEntity){
        viewModelScope.launch(Dispatchers.IO){
            repositoryIngredients.addIngredient(newIngredient);
        }
    }

    fun deleteIngredient(id : Int){
        viewModelScope.launch(Dispatchers.IO) {
            repositoryIngredients.deleteIngredient(id);
        }
    }

    fun updateIngredient(newIngredient : IngredientEntity){
        viewModelScope.launch(Dispatchers.IO) {
            repositoryIngredients.updateIngredient(newIngredient);
        }
    }

    fun deleteAllIngredients(){
        viewModelScope.launch(Dispatchers.IO) {
            repositoryIngredients.deleteAllIngredients();
        }
    }

    fun addAllRecipes(filename : String){
        viewModelScope.launch(Dispatchers.IO) {
            if(repositoryIngredients.readAllData.value?.isEmpty() == true){
                var dataFromJson = IngredientJsonReader(getApplication(),filename).ingredientEntities
                repositoryIngredients.addAllIngredients(dataFromJson);
            }
        }
    }
}
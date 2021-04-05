package com.example.pickrecipe.ui.favorites

import android.app.Application
import androidx.lifecycle.*
import com.example.pickrecipe.db.data.RecipeIngredientDatabase
import com.example.pickrecipe.db.model.RecipeEntity

class FavoritesViewModel (application: Application) : AndroidViewModel(application) {

    private val database = RecipeIngredientDatabase.getDatabase(application)

    fun getFavoriteRecipes(ids : List<String>): List<RecipeEntity> {
        return database?.recipeDao()?.getFavoriteRecipes(ids)!!
    }

}
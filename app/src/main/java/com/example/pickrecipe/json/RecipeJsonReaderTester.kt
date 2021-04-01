package com.example.pickrecipe.json

import android.content.Context
import android.util.Log
import com.example.pickrecipe.db.model.IngredientEntity
import com.example.pickrecipe.db.model.RecipeEntity
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class RecipeJsonReaderTester (context : Context, jsonString : String) {


    //Refer to Json class file and create a list
//    private var myRecipeJsonType = Types.newParameterizedType(List::class.java, RecipeJson::class.java)
    private var myRecipeJsonType = Types.newParameterizedType(List::class.java, RecipeMoshi::class.java)


    //List of Recipe and Ingredient Entities - Database related
    var recipeEntities : ArrayList<RecipeEntity> = arrayListOf();

    companion object{
        var recipeInventory : ArrayList<RecipeMoshi> = arrayListOf();
    }

    init{

        var moshi : Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        var adapter : JsonAdapter<List<RecipeMoshi>> = moshi.adapter(myRecipeJsonType);

        var recipeListJson = adapter.fromJson(jsonString);

        //Check if the json is empty
        if(!recipeListJson.isNullOrEmpty()){

            for (recipe in recipeListJson) {
                var ingredients : String = ""
                for (ingredient in recipe.ingredients) {
                    ingredients += "${ingredient.qty} ${ingredient.name}|"
                }

                var comments : String = ""
                for (comment in recipe.comments) {
                    comments += "$comment|"
                }

                //Create a recipeEntity Object
                var recipe = RecipeEntity(
                    recipe.id,
                    recipe.recipeTitle,
                    recipe.details,
                    ingredients,
                    recipe.directions,
                    recipe.rating,
                    comments,
                    recipe.picture,
                    recipe.tags
                )

                //Add the recipe object to the list
                recipeEntities.add(recipe);
            }
        }
    }
}
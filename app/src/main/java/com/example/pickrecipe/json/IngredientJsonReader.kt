package com.example.pickrecipe.json

import android.content.Context
import com.example.pickrecipe.db.model.IngredientEntity
import com.example.pickrecipe.db.model.RecipeEntity
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class IngredientJsonReader (context : Context, fileName : String) {

    private var myIngredientJsonType = Types.newParameterizedType(List::class.java, RecipeJson::class.java)

    var ingredientEntities : ArrayList<IngredientEntity> = arrayListOf();

    companion object{
        var ingredientInventory : ArrayList<IngredientJ> = arrayListOf();
    }

    init{
        var jsonContent = FileReaderHelper.getDataFromAssets(context,fileName);
        var moshi : Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        var adapter : JsonAdapter<List<RecipeJson>> = moshi.adapter(myIngredientJsonType);

        var recipeListJson = adapter.fromJson(jsonContent);

        if(!recipeListJson.isNullOrEmpty()){
            for((i, newRecipe) in recipeListJson.withIndex()){
                //A loop to get every ingredient from the json ingredientList, from each recipe
                for((j,newIngredient) in newRecipe.ingredients.withIndex()){
                    var ingredient = IngredientEntity(
                        newIngredient.ingredientId,
                        newIngredient.detail,
                        newIngredient.unity,
                        newIngredient.quantity,
                        newIngredient.glutenFree
                    );
                    //Add the ingredient to a list of ingredients
                    ingredientEntities.add(ingredient)
                }
            }
        }
    }
}
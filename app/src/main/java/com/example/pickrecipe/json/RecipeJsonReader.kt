package com.example.pickrecipe.json

import android.content.Context
import com.example.pickrecipe.db.model.IngredientEntity
import com.example.pickrecipe.db.model.RecipeEntity
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class RecipeJsonReader (context : Context, fileName : String) {

    private var myRecipeJsonType = Types.newParameterizedType(List::class.java, RecipeJson::class.java)

    var recipeEntities : ArrayList<RecipeEntity> = arrayListOf();

    companion object{
        var recipeInventory : ArrayList<RecipeJson> = arrayListOf();
    }

    init{

        var jsonContent = FileReaderHelper.getDataFromAssets(context,fileName);
        var moshi : Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        var adapter : JsonAdapter<List<RecipeJson>> = moshi.adapter(myRecipeJsonType);

        var recipeListJson = adapter.fromJson(jsonContent);

        //Check if the json is empty
        if(!recipeListJson.isNullOrEmpty()){

            //Loop to get every record from json
            for((i, newRecipe) in recipeListJson.withIndex()){

                //Every recipe has a list of ingredients.
                var ingredientList : ArrayList<IngredientEntity> = arrayListOf();

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
                    ingredientList.add(ingredient)
                }

                //Create a recipeEntity Object
                var recipe = RecipeEntity(
                    newRecipe.id,
                    newRecipe.recipeTitle,
                    newRecipe.rating,
                    newRecipe.details,
                    newRecipe.directions,
                    newRecipe.picture,
                    //add the ingredient list to the Recipe Entity Object
                    //ingredientList
                );

                //Add the recipe object to the list
                this.recipeEntities.add(recipe)
            }
        }
    }
}
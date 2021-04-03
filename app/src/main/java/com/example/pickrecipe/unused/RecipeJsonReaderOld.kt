package com.example.pickrecipe.unused

import android.content.Context

class RecipeJsonReaderOld (context : Context, fileName : String) {


//    //Refer to Json class file and create a list
//    private var myRecipeJsonType = Types.newParameterizedType(List::class.java, RecipeJson::class.java)
//
//
//    //List of Recipe and Ingredient Entities - Database related
//    var recipeEntities : ArrayList<RecipeEntity> = arrayListOf();
//    var ingredientEntities : ArrayList<IngredientEntity> = arrayListOf();
//
//    companion object{
//        var recipeInventory : ArrayList<RecipeJson> = arrayListOf();
//    }
//
//    init{
//
//        var jsonContent = FileReaderHelper.getDataFromAssets(context,fileName);
//        var moshi : Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
//        var adapter : JsonAdapter<List<RecipeJson>> = moshi.adapter(myRecipeJsonType);
//
//        var recipeListJson = adapter.fromJson(jsonContent);
//
//        //Check if the json is empty
//        if(!recipeListJson.isNullOrEmpty()){
//
//            //Loop to get every record from json
//            for((i, newRecipe) in recipeListJson.withIndex()){
//                var recipeIngredient : String = "";
//                //A loop to get every ingredient from the json ingredientList, from each recipe
//                for((j,newIngredient) in newRecipe.ingredients.withIndex()){
//                    var ingredient = IngredientEntity(
//                        newIngredient.ingredientId,
//                        newRecipe.id,
//                        newIngredient.detail,
//                        newIngredient.unity,
//                        newIngredient.quantity,
//                        newIngredient.glutenFree
//                    );
//                    recipeIngredient += newIngredient.quantity.toString()+"|";
//                    recipeIngredient += newIngredient.unity+"|";
//                    recipeIngredient += newIngredient.detail+"|";
//                    //recipeIngredient += newIngredient.ingredientId
//                    //Add the ingredient to a list of ingredients
//                    ingredientEntities.add(ingredient)
//                }
//
//                //Create a recipeEntity Object
//                var recipe = RecipeEntity(
//                    newRecipe.id,
//                    newRecipe.recipeTitle,
//                    newRecipe.rating,
//                    newRecipe.details,
//                    newRecipe.directions,
//                    newRecipe.picture,
//                    recipeIngredient
//                );
//
//                //Add the recipe object to the list
//                recipeEntities.add(recipe);
//            }
//        }
//    }
}
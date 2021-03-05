package com.example.pickrecipe.json

import com.squareup.moshi.Json

data class RecipeJson (
    @Json(name = "id")          var id          : String,
    @Json(name = "recipeTitle") var recipeTitle : String,
    @Json(name = "details")     var details     : String,
    @Json(name = "rating")      var rating      : Double,
    @Json(name = "directions")  var directions  : String,
    @Json(name = "picture")     var picture     : String,
    @Json(name = "ingredients") var ingredients : List<IngredientJ>
)

data class IngredientJ(
    @Json(name = "ingredientId") var ingredientId : String,
    @Json(name = "detail")       var detail       : String,
    @Json(name = "unity")        var unity        : String,
    @Json(name = "glutenFree")   var glutenFree   : Boolean = false,
    @Json(name = "quantity")     var quantity     : Double
)
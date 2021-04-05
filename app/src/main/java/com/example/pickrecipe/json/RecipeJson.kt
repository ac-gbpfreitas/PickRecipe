package com.example.pickrecipe.json

import com.squareup.moshi.Json

data class UserJson(
    var username : String,
    var password : String,
    var _id : Id,
    var pantry : List<String>,
    var favorites : List<String>
)

//this is how object ID's come from the backend. We can use it for any entity.
data class Id(
        @Json(name = "\$oid") var id : String
)

//for home fragment
data class RecipeMoshi (
        @Json(name = "id")          var id          : String,
        @Json(name = "title")       var recipeTitle : String,
        @Json(name = "details")     var details     : String,
        @Json(name = "ingredients") var ingredients : List<IngredientMoshi>,
        @Json(name = "directions")  var directions  : String,
        @Json(name = "rating")      var rating      : Double,
        @Json(name = "comments")    var comments    : List<String>,
        @Json(name = "pic")         var picture     : String,
        @Json(name = "tags")        var tags        : String,
)

data class IngredientMoshi (
        var qty : String,
        var name : String
)

/*UNUSED CLASSES*/
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


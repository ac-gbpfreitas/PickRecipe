package com.example.pickrecipe.model

import com.example.pickrecipe.unused.Ingredient

class Recipe {

    private var recipeId : String = "0";
    private var rating: Double = 0.0;
    private var picture : String = "";
    private var ingredients : String = "";
    private var pantryCheck : String
    private var isFavorite : Boolean = false
    private var tags : String = ""

    private var details: String;
    private var recipeTitle : String;
    private var directions: String;
    private var comments: String

    constructor(
        id : String = "",
        title : String,
        details : String = "",
        directions : String = "",
        rating : Double = 0.0,
        pic : String = "",
        ingredients : String = "",
        comments : String = "",
        pantryCheck : String,
        isFavorite : Boolean,
        tags : String
    ){
        this.recipeId = id;
        this.recipeTitle = title;
        this.details = details;
        this.directions = directions;
        this.rating = rating;
        this.picture = pic;
        this.ingredients = ingredients
        this.comments = comments
        this.pantryCheck = pantryCheck
        this.isFavorite = isFavorite
        this.tags = tags

    }
    public fun getRecipeId() : String{
        return this.recipeId;
    }
    public fun setRecipeId(newId : String){
        this.recipeId = newId;
    }

    public fun getRecipeComments() : String {
        return this.comments
    }

    public fun setRecipeComments(comment : String) {
        this.comments += comment
    }

    public fun getRecipeTitle() : String{
        return this.recipeTitle;
    }
    public fun setRecipeTitle(newTitle : String){
        this.recipeTitle = newTitle;
    }

    public fun getRating(): Double{
        return this.rating;
    }
    public fun setRating(newRate: Double){
        this.rating = newRate;
    }

    public fun getIngredients(): String{
        return this.ingredients;
    }

    public fun setIngredients(ingredients: String){
        this.ingredients = ingredients;
    }

    public fun getDetails(): String{
        return this.details;
    }
    public fun setDetails(newDescription: String){
        this.details = newDescription;
    }

    public fun getDirections(): String{
        return this.directions;
    }
    public fun setDirections(newDirection: String){
        this.directions = newDirection;
    }

    public fun getPicture(): String{
        return this.picture;
    }
    public fun setPicture(pic:String){
        this.picture = pic;
    }

    public fun getPantryCheck() : String {
        return this.pantryCheck
    }

    public fun getIsFavorite() : Boolean {
        return this.isFavorite
    }

    public fun getTags() : String {
        return this.tags
    }

}
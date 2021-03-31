package com.example.pickrecipe.model

class Recipe {

    private var recipeId : String = "0";
    private var rating: Double = 0.0;
    private var picture : String = "";
    private var ingredients : String = "";
    private var ingredientsList : ArrayList<Ingredient> = arrayListOf();
    private lateinit var pantryCheck : String

    private lateinit var details: String;
    private lateinit var recipeTitle : String;
    private lateinit var directions: String;
    private lateinit var comments: String
    //private var pictures: ArrayList<Int> = arrayListOf();
    //private var videos: ArrayList<Int> = arrayListOf();

    constructor(
        id : String = "",
        title : String,
        details : String = "",
        directions : String = "",
        rating : Double = 0.0,
        pic : String = "",
        ingredients : String = "",
        comments : String = "",
        pantryCheck : String
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

    public fun setIngredientsList(newList : ArrayList<Ingredient>){
        this.ingredientsList = newList;
    }
    public fun getIngredientsLists() : ArrayList<Ingredient>{
        return this.ingredientsList;
    }

    public fun getPantryCheck() : String {
        return this.pantryCheck
    }


    /*
    public fun getPictures(): List<Int>{
        return this.pictures;
    }
    public fun setPictures(picsList: List<Int>){
        this.pictures = picsList;
    }


    public fun getVideos(): ArrayList<Int>{
        return this.videos;
    }
    public fun setVideos(videosList: ArrayList<Int>){
        this.videos = videosList;
    }
    */
}
package com.example.pickrecipe.model

class Recipe {

    private var recipeId : Int = 0;
    private var rating: Double = 0.0;
    private var picture : String = "";
    private var ingredientsList : List<Ingredient> = arrayListOf();

    private lateinit var details: String;
    private lateinit var recipeTitle : String;
    private lateinit var directions: String;

    //private var pictures: ArrayList<Int> = arrayListOf();
    //private var videos: ArrayList<Int> = arrayListOf();

    constructor(
        id : Int,
        title : String,
        details : String = "",
        directions : String = "",
        rating : Double = 0.0,
        pic : String = "",
        ingredients : List<Ingredient>
    ){
        this.recipeId = id;
        this.recipeTitle = title;
        this.details = details;
        this.directions = directions;
        this.rating = rating;
        this.picture = pic;
        this.ingredientsList = ingredients

    }
    public fun getRecipeId() : Int{
        return this.recipeId;
    }
    public fun setRecipeId(newId : Int){
        this.recipeId = newId;
    }

    public fun getRecipeTitle() : String{
        return this.recipeTitle;
    }
    public fun setRecipeTitle(newTitle : String){
        this.recipeTitle = newTitle;
    }

    public fun getRate(): Double{
        return this.rating;
    }
    public fun setRating(newRate: Double){
        this.rating = newRate;
    }

    public fun getIngredients(): List<Ingredient>{
        return this.ingredientsList;
    }

    public fun setIngredients(ingredients: List<Ingredient>){
        this.ingredientsList = ingredients;
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
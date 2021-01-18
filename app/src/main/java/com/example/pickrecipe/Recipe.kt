package com.example.pickrecipe

class Recipe {
    //Test comment to github
    private var ingredientsList = ArrayList<Ingredient>();
    private var rate: Double = 0.0;
    private lateinit var description: String;
    private lateinit var directions: String;
    private var pictures: ArrayList<Int> = arrayListOf();
    private var videos: ArrayList<Int> = arrayListOf();

    public fun getRate(): Double{
        return this.rate;
    }
    public fun setRate(newRate: Double){
        this.rate = newRate;
    }

    public fun getIngredients(): ArrayList<Ingredient>{
        return this.ingredientsList;
    }

    public fun setIngredients(ingredients: ArrayList<Ingredient>){
        this.ingredientsList = ingredients;
    }

    public fun getDescritpion(): String{
        return this.description;
    }
    public fun setDescription(newDescription: String){
        this.description = newDescription;
    }

    public fun getDirections(): String{
        return this.directions;
    }
    public fun setDirections(newDirection: String){
        this.directions = newDirection;
    }

    public fun getPictures(): ArrayList<Int>{
        return this.pictures;
    }
    public fun setPictures(picsList: ArrayList<Int>){
        this.pictures = picsList;
    }

    public fun getVideos(): ArrayList<Int>{
        return this.videos;
    }
    public fun setVideos(videosList: ArrayList<Int>){
        this.videos = videosList;
    }

}
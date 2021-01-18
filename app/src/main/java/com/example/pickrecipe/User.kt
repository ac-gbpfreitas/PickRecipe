package com.example.pickrecipe

class User {

    private lateinit var firstName: String;
    private lateinit var lastName: String;
    private lateinit var username: String
    private var profilePic: Int = 0;
    private var bookmarksRecipe: ArrayList<Recipe> = arrayListOf();
    private var userRecipesGallery: ArrayList<Int> = arrayListOf();


    public fun getFirstName(): String{
        return this.firstName;
    }
    public fun setFirstName(fName: String){
        this.firstName = fName;
    }

    public fun getLastName(): String{
        return this.lastName;
    }
    public fun setLastName(lName: String){
        this.lastName = lName;
    }

    public fun getUsername(): String{
        return this.username;
    }
    public fun setUsername(login: String){
        this.username = login;
    }

    public fun getProfilePic(): Int{
        return this.profilePic;
    }
    public fun setProfilePic(pic: Int){
        this.profilePic = pic;
    }

    public fun getBookMarksRecipe(): ArrayList<Recipe>{
        return this.bookmarksRecipe;
    }
    public fun setBookMarksRecipe(recipeList: ArrayList<Recipe>){
        this.bookmarksRecipe = recipeList;
    }

    public fun getUserRecipesGallert(): ArrayList<Int>{
        return this.userRecipesGallery;
    }
    public fun setUserRecipesGallert(galleryList: ArrayList<Int>){
        this.userRecipesGallery = galleryList;
    }

}
package com.example.pickrecipe.unused

import java.io.FileDescriptor

class Ingredient {

    private var ingredientId : String = "";
    private var recipeId : String = "";
    private lateinit var ingredientDescription : String;
    private lateinit var unity : String;
    private var quantity : Double = 0.0;
    private var glutenFree : Boolean = false;

    constructor(
        id : String = "0",
        recipe : String = "0",
        description : String = "",
        unity : String = "",
        qtd : Double = 0.0,
        gluten : Boolean
    ){
        this.ingredientId = id;
        this.recipeId = recipe;
        this.ingredientDescription = description;
        this.unity = unity;
        this.quantity = qtd;
        this.glutenFree = gluten
    }


    public fun getIngredientId() : String {
        return this.ingredientId;
    }
    public fun setIngredientId(newId : String){
        this.ingredientId = newId;
    }

    public fun getRecipeId() : String{
        return this.recipeId;
    }
    public fun setRecipeId(id : String){
        this.recipeId = id;
    }

    public fun getUnity(): String {
        return this.unity;
    }
    public fun setUnity(newUnity: String){
        this.unity = newUnity;
    }

    public fun getQuantity() : Double{
        return this.quantity;
    }
    public fun setQuantity(newQuantity : Double){
        this.quantity = newQuantity;
    }

    public fun getIngredientDescription(): String{
        return this.ingredientDescription;
    }
    public fun setIngredientDescription(newDescription: String){
        this.ingredientDescription = newDescription;
    }

    public fun getGlutenFree(): Boolean{
        return this.glutenFree;
    }
    public fun setGlutenFree(gluten: Boolean){
        this.glutenFree = true;
    }

}
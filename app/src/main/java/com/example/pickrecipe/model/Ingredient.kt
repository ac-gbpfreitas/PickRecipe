package com.example.pickrecipe.model

import java.io.FileDescriptor

class Ingredient {

    private var ingredientId : Int = 0;
    private lateinit var ingredientDescription: String;
    private lateinit var unity: String;
    private var quantity: Int = 0;
    private var glutenFree: Boolean = false;

    constructor(
        id : Int = 0,
        description : String = "",
        unity : String = "",
        qtd : Int = 0,
        gluten : Boolean
    ){
        this.ingredientId = id;
        this.ingredientDescription = description;
        this.unity = unity;
        this.quantity = qtd;
        this.glutenFree = gluten
    }


    public fun getIngredientId() : Int {
        return this.ingredientId;
    }
    public fun setIngredientId(newId : Int){
        this.ingredientId = newId;
    }

    public fun getUnity(): String {
        return this.unity;
    }
    public fun setUnity(newUnity: String){
        this.unity = newUnity;
    }

    public fun getQuantity(): Int{
        return this.quantity;
    }
    public fun setQuantity(newQuantity: Int){
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
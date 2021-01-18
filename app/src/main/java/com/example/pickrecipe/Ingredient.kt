package com.example.pickrecipe

import java.io.FileDescriptor

class Ingredient {
    //Test comment to github
    private lateinit var ingredientDescription: String;
    private lateinit var unity: String;
    private var quantity: Int = 0;

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

}
package com.example.pickrecipe.db.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "recipe")
data class  RecipeEntity (
    @PrimaryKey
    var id : Int,
    var recipeTitle : String = "",
    var rating: Double = 0.0,
    var details: String = "",
    var directions: String = "",
    var picture: String = "",
    //var ingredientsList : List<IngredientEntity> = arrayListOf()
    //var pictures: List<Int> = arrayListOf(),
    //var videos: List<Int> = arrayListOf()
) : Parcelable
    /*
    constructor(
        id: Int,
        recipeTitle: String,
        rating: Double,
        details: String,
        directions: String,
        pictures: List<String>,
        ingredientsList: List<IngredientEntity>
    ) : this(
        id,
        recipeTitle,
        rating,
        details,
        directions,
        pictures,
        ingredientsList
    )
    */
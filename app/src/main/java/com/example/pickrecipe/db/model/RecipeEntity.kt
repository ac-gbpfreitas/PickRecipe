package com.example.pickrecipe.db.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "recipe")
data class  RecipeEntity (
    @PrimaryKey
    var recipeId : String,
    var recipeTitle : String = "",
    var details : String = "",
    var ingredients : String = "",
    var directions : String = "",
    var rating : Double = 0.0,
    var comments : String = "",
    var picture : String = "",
    var tags : String = ""

) : Parcelable
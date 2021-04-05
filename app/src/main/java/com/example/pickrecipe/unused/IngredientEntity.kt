package com.example.pickrecipe.unused

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "ingredient")
data class IngredientEntity (
    @PrimaryKey
    var ingredientId : String,
    var recipeId     : String = "",
    var detail       : String,
    var unity        : String,
    var quantity     : Double = 0.0,
    var glutenFree   : Boolean = false
) : Parcelable
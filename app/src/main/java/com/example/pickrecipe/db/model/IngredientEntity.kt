package com.example.pickrecipe.db.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "ingredient")
data class IngredientEntity (
    @PrimaryKey
    var id : Int,
    var detail: String,
    var unity: String,
    var quantity: Int = 0,
    var glutenFree: Boolean = false
) : Parcelable
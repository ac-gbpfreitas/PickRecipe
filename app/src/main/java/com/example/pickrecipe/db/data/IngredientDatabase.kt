package com.example.pickrecipe.db.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pickrecipe.db.model.IngredientEntity

@Database(entities = [IngredientEntity::class], version = 1, exportSchema = false)
abstract class IngredientDatabase : RoomDatabase(){

    abstract fun ingredientDao() : IngredientDao

    companion object{
        @Volatile
        private var INSTANCE : IngredientDatabase? = null;

        fun getDatabase(context : Context) : IngredientDatabase?{
            if(INSTANCE == null){
                synchronized(IngredientDatabase::class){
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        IngredientDatabase::class.java,
                        "RecipeDatabase.db"
                    ).build();
                }
            }
            return INSTANCE;
        }
    }
}
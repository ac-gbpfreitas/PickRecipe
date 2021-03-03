package com.example.pickrecipe.db.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pickrecipe.db.model.RecipeEntity

@Database(entities = [RecipeEntity::class], version = 1, exportSchema = false)
abstract class RecipeDatabase : RoomDatabase() {

    abstract fun recipeDao() : RecipeDao

    companion object{
        @Volatile
        private var INSTANCE : RecipeDatabase? = null;

        fun getDatabase(context : Context) : RecipeDatabase?{
            if(INSTANCE == null){
                synchronized(IngredientDatabase::class){
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        RecipeDatabase::class.java,
                        "RecipeDatabase.db"
                    ).build();
                }
            }
            return INSTANCE;
        }
    }
}
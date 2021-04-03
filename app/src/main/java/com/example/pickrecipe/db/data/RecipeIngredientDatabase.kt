package com.example.pickrecipe.db.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pickrecipe.unused.IngredientEntity
import com.example.pickrecipe.db.model.RecipeEntity
import com.example.pickrecipe.unused.IngredientDao


@Database(entities = [RecipeEntity::class, IngredientEntity::class], version = 1, exportSchema = false)
abstract class RecipeIngredientDatabase : RoomDatabase() {

    abstract fun recipeDao() : RecipeDao
    abstract fun ingredientDao() : IngredientDao;

    companion object{
        @Volatile
        private var INSTANCE : RecipeIngredientDatabase? = null;

        fun getDatabase(context : Context) : RecipeIngredientDatabase?{
            if(INSTANCE == null){
                synchronized(RecipeIngredientDatabase::class){
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        RecipeIngredientDatabase::class.java,
                        "RecipeDatabase.db"
                    ).build();
                }
            }
            return INSTANCE;
        }
    }
}

package com.triad.school.letscook.storage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.triad.school.letscook.dao.IngredientDao
import com.triad.school.letscook.dao.RecipeDao
import com.triad.school.letscook.dao.RecipeIngredientCrossRefDao
import com.triad.school.letscook.dto.Ingredient
import com.triad.school.letscook.dto.Recipe
import com.triad.school.letscook.dto.RecipeIngredientCrossRef

@Database(
    entities = [
        Ingredient::class,
        Recipe::class,
        RecipeIngredientCrossRef::class,
    ],
    version = 11
)
@TypeConverters(value = [Ingredient.IngredientCategory::class])
abstract class RecipeDatabase : RoomDatabase() {

    abstract fun ingredientDao(): IngredientDao
    abstract fun recipeDao(): RecipeDao
    abstract fun recipeIngredientCrossRefDao(): RecipeIngredientCrossRefDao

    companion object {
        private const val databaseFile = "RecipeDatabase.db"

        @Volatile private var INSTANCE: RecipeDatabase? = null

        fun getInstance(context: Context) = INSTANCE ?: synchronized(this) {
            INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            RecipeDatabase::class.java,
            databaseFile
        ).fallbackToDestructiveMigration().build()
    }
}
package com.triad.school.letscook.storage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.triad.school.letscook.dto.Ingredient
import com.triad.school.letscook.dto.IngredientCategory

const val databaseFile = "RecipeDatabase.db"

@Database(entities = [Ingredient::class], version = 2)
@TypeConverters(value = [IngredientCategory::class])
abstract class RecipeDatabase : RoomDatabase() {
    abstract fun ingredientDao(): IngredientDao

    companion object {
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
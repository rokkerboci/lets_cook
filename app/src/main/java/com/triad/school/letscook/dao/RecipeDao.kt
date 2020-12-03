package com.triad.school.letscook.dao

import androidx.room.*
import com.triad.school.letscook.dto.Recipe

@Dao
interface RecipeDao {
    @Query("SELECT * FROM Recipe")
    suspend fun getAll(): List<Recipe>

    @Query("SELECT * FROM Recipe WHERE recipeId == :id")
    suspend fun getFromId(id: Long): Recipe

    @Insert
    suspend fun insert(recipe: Recipe): Long

    @Update
    suspend fun update(recipe: Recipe)

    @Delete
    suspend fun delete(recipe: Recipe)
}
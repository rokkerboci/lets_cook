package com.triad.school.letscook.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.triad.school.letscook.dto.Ingredient

@Dao
interface IngredientDao {
    @Query("SELECT * FROM Ingredient")
    suspend fun getAll(): List<Ingredient>

    @Insert
    suspend fun insert(ingredient: Ingredient): Long

    @Delete
    suspend fun delete(ingredient: Ingredient)
}
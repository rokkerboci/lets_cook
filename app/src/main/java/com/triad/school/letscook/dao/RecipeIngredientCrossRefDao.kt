package com.triad.school.letscook.dao

import androidx.room.*
import com.triad.school.letscook.dto.RecipeIngredientCrossRef
import com.triad.school.letscook.storage.IngredientWithAmount

@Dao
interface RecipeIngredientCrossRefDao {
    @Query("""
        SELECT 
            Recipe.*,
            Ingredient.*,
            RecipeIngredientCrossRef.amount
        FROM Recipe
        INNER JOIN RecipeIngredientCrossRef ON (RecipeIngredientCrossRef.recipeId = Recipe.recipeId)
        INNER JOIN Ingredient ON (RecipeIngredientCrossRef.ingredientId = Ingredient.ingredientId)
        WHERE Recipe.recipeId = :recipeId
        """
    )
    suspend fun getIngredients(recipeId: Long): List<IngredientWithAmount>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recipeIngredientCrossRef: RecipeIngredientCrossRef)

    @Delete
    suspend fun delete(recipeIngredientCrossRef: RecipeIngredientCrossRef)
}
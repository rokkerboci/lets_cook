package com.triad.school.letscook.storage

import androidx.room.Embedded
import com.triad.school.letscook.dto.Ingredient

data class IngredientWithAmount(
    @Embedded val ingredient: Ingredient,
    val amount: Double,
) {
    companion object {
        val Default = IngredientWithAmount(
            ingredient = Ingredient.Default,
            amount = 0.0
        )
    }
}
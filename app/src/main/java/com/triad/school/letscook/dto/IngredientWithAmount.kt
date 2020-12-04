package com.triad.school.letscook.dto

import androidx.room.Embedded

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
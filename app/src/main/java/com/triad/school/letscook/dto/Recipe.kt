package com.triad.school.letscook.dto

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
data class Recipe(
    @PrimaryKey(autoGenerate = true)
    val recipeId: Long,
    val recipeName: String,
    val recipeDescription: String,
) {
    companion object {
        val Default = Recipe(
            recipeId = 0,
            recipeName = "",
            recipeDescription = ""
        )
    }
}

data class RecipeWithAmount(
    val recipe: Recipe,
    val amount: Long
) {
    companion object {
        val Default = RecipeWithAmount(
            recipe = Recipe.Default,
            amount = 0
        )
    }
}

@Parcelize
data class RecipeWithAmountDescriptor (
    val recipeId: Long,
    val amount: Long
) : Parcelable
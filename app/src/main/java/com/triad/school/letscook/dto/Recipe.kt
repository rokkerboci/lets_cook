package com.triad.school.letscook.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

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
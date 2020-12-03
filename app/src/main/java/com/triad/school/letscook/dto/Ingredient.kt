package com.triad.school.letscook.dto

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.triad.school.letscook.R

@Entity
data class Ingredient(
    @PrimaryKey(autoGenerate = true)
    val ingredientId: Long,
    val ingredientName: String,
    val ingredientUnit: String,
    val ingredientCategory: IngredientCategory,
) {
    companion object {
        val Default = Ingredient(
            ingredientId = 0,
            ingredientName = "",
            ingredientUnit = "",
            ingredientCategory = IngredientCategory.Dairy
        )
    }

    enum class IngredientCategory(
        @StringRes val stringResourceId: Int,
        @DrawableRes val drawableResourceId: Int
    ) {
        Dairy(R.string.ingredient_dairy, R.drawable.groceries),
        Spice(R.string.ingredient_spice, R.drawable.groceries),
        Vegetable(R.string.ingredient_vegetable, R.drawable.groceries),
        Fruit(R.string.ingredient_fruit, R.drawable.groceries),
        Meat(R.string.ingredient_meat, R.drawable.groceries),
        Other(R.string.ingredient_other, R.drawable.groceries);

        companion object {
            @JvmStatic
            @TypeConverter
            fun getByOrdinal(ordinal: Int) = values().first { it.ordinal == ordinal }

            @JvmStatic
            @TypeConverter
            fun toInt(ingredientCategory: IngredientCategory) = ingredientCategory.ordinal
        }

    }

}

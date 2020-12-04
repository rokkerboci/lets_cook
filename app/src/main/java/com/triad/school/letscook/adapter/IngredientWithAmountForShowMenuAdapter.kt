package com.triad.school.letscook.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.triad.school.letscook.R
import com.triad.school.letscook.dto.IngredientWithAmount
import kotlin.properties.Delegates

class IngredientWithAmountForShowMenuAdapter : RecyclerView.Adapter<IngredientWithAmountForShowMenuAdapter.ViewHolder>() {
    private val ingredients = mutableListOf<IngredientWithAmount>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.ingredient_item_for_show, parent, false)
    )

    override fun getItemCount() = ingredients.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.item = ingredients[position]
    }

    fun setItems(items: List<IngredientWithAmount>) {
        ingredients.clear()
        ingredients.addAll(items)

        notifyDataSetChanged()
        notifyItemRangeChanged(0, ingredients.size)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val iconImageView = itemView.findViewById<ImageView>(R.id.IngredientItemIconImageView)
        private val nameTextView = itemView.findViewById<TextView>(R.id.IngredientItemNameTextView)
        private val amountTextView = itemView.findViewById<TextView>(R.id.IngredientItemAmountTextView)
        private val unitTextView = itemView.findViewById<TextView>(R.id.IngredientItemUnitTextView)

        var item: IngredientWithAmount by Delegates.observable(IngredientWithAmount.Default) { _, _, value ->
            iconImageView.setImageResource(value.ingredient.ingredientCategory.drawableResourceId)
            nameTextView.text = value.ingredient.ingredientName
            unitTextView.text = value.ingredient.ingredientUnit
            amountTextView.text = value.amount.toString()
        }
    }
}
package com.triad.school.letscook.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.triad.school.letscook.R
import com.triad.school.letscook.dto.Ingredient
import kotlin.properties.Delegates

class IngredientAdapter : RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder>() {
    private val ingredients = mutableListOf<Ingredient>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = IngredientViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.ingredient_item, parent, false)
    )

    override fun getItemCount() = ingredients.size

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        holder.item = ingredients[position]
    }

    fun addItem(item: Ingredient) {
        ingredients.add(item)

        notifyItemInserted(ingredients.size)
    }

    fun removeItem(item: Ingredient) {
        val index = ingredients.indexOf(item)
        ingredients.removeAt(index)

        notifyItemRemoved(index)
    }

    fun setItems(items: List<Ingredient>) {
        ingredients.clear()
        ingredients.addAll(items)

        notifyDataSetChanged()
    }

    inner class IngredientViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val iconImageView = itemView.findViewById<ImageView>(R.id.IngredientIconImageView)
        private val nameTextView = itemView.findViewById<TextView>(R.id.IngredientNameTextView)
        private val categoryTextView = itemView.findViewById<TextView>(R.id.IngredientTypeTextView)
        private val unitTextView = itemView.findViewById<TextView>(R.id.IngredientUnitTextView)
        private val removeButton = itemView.findViewById<ImageButton>(R.id.IngredientRemoveButton)

        var item: Ingredient by Delegates.observable(Ingredient.Default) { _, _, value ->
            iconImageView.setImageResource(value.ingredientCategory.drawableResourceId)
            nameTextView.text = value.name
            categoryTextView.text = value.ingredientCategory.name
            unitTextView.text = value.unit
        }

        init {
            removeButton.setOnClickListener {
                removeItem(item)
            }
        }
    }
}

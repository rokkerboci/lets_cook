package com.triad.school.letscook.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.triad.school.letscook.R
import com.triad.school.letscook.dto.Ingredient
import kotlin.properties.Delegates

class IngredientAdapter(
    private val onRemoveButtonClicked: (Ingredient) -> Unit
) : RecyclerView.Adapter<IngredientAdapter.ViewHolder>() {
    private val ingredients = mutableListOf<Ingredient>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.ingredient_item, parent, false)
    )

    override fun getItemCount() = ingredients.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
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

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val iconImageView = itemView.findViewById<ImageView>(R.id.IngredientItemIconImageView)
        private val nameTextView = itemView.findViewById<TextView>(R.id.IngredientItemNameTextView)
        private val categoryTextView = itemView.findViewById<TextView>(R.id.IngredientItemTypeTextView)
        private val unitTextView = itemView.findViewById<TextView>(R.id.IngredientItemUnitTextView)
        private val removeButton = itemView.findViewById<ImageButton>(R.id.IngredientItemRemoveButton)

        var item: Ingredient by Delegates.observable(Ingredient.Default) { _, _, value ->
            iconImageView.setImageResource(value.ingredientCategory.drawableResourceId)
            nameTextView.text = value.ingredientName
            categoryTextView.text = value.ingredientCategory.name
            unitTextView.text = value.ingredientUnit
        }

        init {
            removeButton.setOnClickListener {
                onRemoveButtonClicked(item)
            }
        }
    }
}

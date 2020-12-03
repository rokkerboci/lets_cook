package com.triad.school.letscook.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.triad.school.letscook.R
import com.triad.school.letscook.storage.IngredientWithAmount
import kotlin.properties.Delegates

class IngredientWithAmountAdapter(
    private val onRemoveButtonClicked: (IngredientWithAmount) -> Unit
) : RecyclerView.Adapter<IngredientWithAmountAdapter.ViewHolder>() {
    private val ingredients = mutableListOf<IngredientWithAmount>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.ingredient_with_amount_item, parent, false)
    )

    override fun getItemCount() = ingredients.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.item = ingredients[position]
    }

    fun addItem(item: IngredientWithAmount) {
        ingredients.find { it.ingredient == item.ingredient }?.let {
            removeItem(it)
        }

        ingredients.add(item)

        notifyItemInserted(ingredients.size)
    }

    fun removeItem(item: IngredientWithAmount) {
        val index = ingredients.indexOf(item)
        ingredients.removeAt(index)

        notifyItemRemoved(index)
    }

    fun setItems(items: List<IngredientWithAmount>) {
        ingredients.clear()
        ingredients.addAll(items)

        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val iconImageView = itemView.findViewById<ImageView>(R.id.IngredientItemIconImageView)
        private val nameTextView = itemView.findViewById<TextView>(R.id.IngredientItemNameTextView)
        private val unitTextView = itemView.findViewById<TextView>(R.id.IngredientItemUnitTextView)
        private val amountTextView = itemView.findViewById<TextView>(R.id.IngredientItemAmountTextView)
        private val removeButton = itemView.findViewById<ImageButton>(R.id.IngredientItemRemoveButton)

        var item: IngredientWithAmount by Delegates.observable(IngredientWithAmount.Default) { _, _, value ->
            iconImageView.setImageResource(value.ingredient.ingredientCategory.drawableResourceId)
            nameTextView.text = value.ingredient.ingredientName
            unitTextView.text = value.ingredient.ingredientUnit
            amountTextView.text = value.amount.toString()
        }

        init {
            removeButton.setOnClickListener {
                onRemoveButtonClicked(item)
            }
        }
    }
}
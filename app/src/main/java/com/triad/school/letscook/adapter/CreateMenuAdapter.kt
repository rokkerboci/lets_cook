package com.triad.school.letscook.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.triad.school.letscook.R
import com.triad.school.letscook.dto.RecipeWithAmount
import kotlin.properties.Delegates

class CreateMenuAdapter(
    private val onRemoveButtonClicked: (RecipeWithAmount) -> Unit
) : RecyclerView.Adapter<CreateMenuAdapter.ViewHolder>() {
    private val recipes = mutableListOf<RecipeWithAmount>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.recipe_with_amount_item, parent, false)
    )

    override fun getItemCount() = recipes.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.item = recipes[position]
    }

    fun addItem(item: RecipeWithAmount) {
        recipes.find { it.recipe == item.recipe }?.let {
            removeItem(it)
        }

        recipes.add(item)

        notifyItemInserted(recipes.size)
    }

    fun removeItem(item: RecipeWithAmount) {
        val index = recipes.indexOf(item)
        recipes.removeAt(index)

        notifyItemRemoved(index)
    }

    fun setItems(items: List<RecipeWithAmount>) {
        recipes.clear()
        recipes.addAll(items)

        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView = itemView.findViewById<TextView>(R.id.RecipeItemNameTextView)
        private val amountTextView = itemView.findViewById<TextView>(R.id.RecipeItemAmountTextView)
        private val removeButton = itemView.findViewById<ImageButton>(R.id.RecipeItemRemoveButton)

        var item: RecipeWithAmount by Delegates.observable(RecipeWithAmount.Default) { _, _, value ->
            nameTextView.text = value.recipe.recipeName
            amountTextView.text = value.amount.toString()
        }

        init {
            removeButton.setOnClickListener {
                onRemoveButtonClicked(item)
            }
        }
    }
}
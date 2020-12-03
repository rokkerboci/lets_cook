package com.triad.school.letscook.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.triad.school.letscook.R
import com.triad.school.letscook.dto.Recipe
import kotlin.properties.Delegates

class RecipeAdapter(
    private val onRemoveButtonClicked: (Recipe) -> Unit,
    private val onEditButtonClicked: (Recipe) -> Unit,
)  : RecyclerView.Adapter<RecipeAdapter.ViewHolder>() {
    private val recipes = mutableListOf<Recipe>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.recipe_item, parent, false)
    )

    override fun getItemCount() = recipes.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.item = recipes[position]
    }

    fun addItem(item: Recipe) {
        recipes.add(item)

        notifyItemInserted(recipes.size)
    }

    fun removeItem(item: Recipe) {
        val index = recipes.indexOf(item)
        recipes.removeAt(index)

        notifyItemRemoved(index)
    }

    fun setItems(items: List<Recipe>) {
        recipes.clear()
        recipes.addAll(items)

        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView = itemView.findViewById<TextView>(R.id.RecipeItemNameTextView)
        private val removeButton = itemView.findViewById<ImageButton>(R.id.RecipeItemRemoveButton)
        private val editButton = itemView.findViewById<ImageButton>(R.id.RecipeItemEditButton)

        var item: Recipe by Delegates.observable(Recipe.Default) { _, _, value ->
            nameTextView.text = value.recipeName
        }

        init {
            removeButton.setOnClickListener {
                onRemoveButtonClicked(item)
            }
            editButton.setOnClickListener {
                onEditButtonClicked(item)
            }
        }
    }
}

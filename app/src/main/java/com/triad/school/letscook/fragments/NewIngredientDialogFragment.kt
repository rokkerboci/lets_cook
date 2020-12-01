package com.triad.school.letscook.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.fragment.app.DialogFragment
import com.triad.school.letscook.R
import com.triad.school.letscook.dto.Ingredient
import com.triad.school.letscook.dto.IngredientCategory

fun interface CreateItemListener<T> {
    fun onItemCreated(item: T)
}

class NewIngredientDialogFragment(
    private val listener: CreateItemListener<Ingredient>
) : DialogFragment() {

    private lateinit var nameEditText: EditText
    private lateinit var unitEditText: EditText
    private lateinit var categorySpinner: Spinner

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.new_ingredient_item)
            .setView(getContentView())
            .setPositiveButton(R.string.ok) { _, _ ->
                if (isValid()) {
                    listener.onItemCreated(ingredient)
                }
            }
            .setNegativeButton(R.string.cancel, null)
            .create()
    }

    private fun isValid() = nameEditText.text.isNotBlank() && unitEditText.text.isNotBlank()

    private val ingredient: Ingredient
        get() = Ingredient(
            id = null,
            name = nameEditText.text.toString(),
            unit = unitEditText.text.toString(),
            ingredientCategory = IngredientCategory.getByOrdinal(categorySpinner.selectedItemPosition)
        )

    private fun getContentView(): View {
        val contentView = LayoutInflater.from(context).inflate(R.layout.new_ingredient_item_dialog, null)
        nameEditText = contentView.findViewById(R.id.IngredientItemNameEditText)
        unitEditText = contentView.findViewById(R.id.IngredientItemUnitEditText)
        categorySpinner = contentView.findViewById<Spinner>(R.id.IngredientItemCategorySpinner).apply {
            adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                IngredientCategory.values().map { getString(it.stringResourceId) }
            )
        }

        return contentView
    }
}
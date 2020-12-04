package com.triad.school.letscook.fragments.dialogs

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
import com.triad.school.letscook.fragments.CreateItemListener

class CreateIngredientDialogFragment(
    private val listener: CreateItemListener<Ingredient>
) : DialogFragment() {

    private val contentView: View by lazy {
        LayoutInflater.from(context).inflate(R.layout.create_ingredient_dialog, null)
    }
    private val nameEditText: EditText by lazy {
        contentView.findViewById(R.id.IngredientItemNameEditText)
    }
    private val unitEditText: EditText by lazy {
        contentView.findViewById(R.id.IngredientItemUnitEditText)
    }
    private val categorySpinner: Spinner by lazy {
        contentView.findViewById<Spinner>(R.id.IngredientItemCategorySpinner)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        categorySpinner.apply {
            adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                Ingredient.IngredientCategory.values().map {
                    getString(it.stringResourceId)
                }
            )
        }

        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.add_existing_ingredient)
            .setView(contentView)
            .setPositiveButton(R.string.ok) { _, _ ->
                if (isValid) {
                    listener.onItemCreated(ingredient)
                }
            }
            .setNegativeButton(R.string.cancel, null)
            .create()
    }

    private val isValid
        get() = nameEditText.text.isNotBlank() && unitEditText.text.isNotBlank()

    private val ingredient: Ingredient
        get() = Ingredient(
            ingredientId = 0,
            ingredientName = nameEditText.text.toString(),
            ingredientUnit = unitEditText.text.toString(),
            ingredientCategory = Ingredient.IngredientCategory.getByOrdinal(categorySpinner.selectedItemPosition)
        )
}
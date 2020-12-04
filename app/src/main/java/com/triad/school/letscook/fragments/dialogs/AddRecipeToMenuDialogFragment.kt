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
import com.triad.school.letscook.dto.Recipe
import com.triad.school.letscook.dto.RecipeWithAmount
import com.triad.school.letscook.fragments.CreateItemListener

class AddRecipeToMenuDialogFragment(
        private val recipes: List<Recipe>,
        private val listener: CreateItemListener<RecipeWithAmount>
) : DialogFragment() {

    private val contentView: View by lazy {
        LayoutInflater.from(context).inflate(R.layout.add_recipe_to_menu_dialog, null)
    }
    private val amountEditText: EditText by lazy {
        contentView.findViewById(R.id.RecipeItemAmountEditText)
    }
    private val recipeSpinner: Spinner by lazy {
        contentView.findViewById(R.id.RecipeItemSpinner)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        recipeSpinner.apply {
            adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                recipes.map {
                    it.recipeName
                }
            )
        }

        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.add_existing_recipe)
            .setView(contentView)
            .setPositiveButton(R.string.ok) { _, _ ->
                if (isValid) {
                    listener.onItemCreated(recipeWithAmount)
                }
            }
            .setNegativeButton(R.string.cancel, null)
            .create()
    }

    private val isValid
        get() = amountEditText.text.isNotBlank() && amountEditText.text.toString().toDoubleOrNull() != null

    private val recipeWithAmount: RecipeWithAmount
        get() = RecipeWithAmount(
            recipe = recipes[recipeSpinner.selectedItemPosition],
            amount = amountEditText.text.toString().toLong()
        )
}
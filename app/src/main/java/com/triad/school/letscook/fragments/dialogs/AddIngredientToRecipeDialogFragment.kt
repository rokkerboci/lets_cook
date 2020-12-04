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
import com.triad.school.letscook.dto.IngredientWithAmount
import com.triad.school.letscook.fragments.CreateItemListener

class AddIngredientToRecipeDialogFragment(
    private val ingredients: List<Ingredient>,
    private val listener: CreateItemListener<IngredientWithAmount>
) : DialogFragment() {

    private val contentView: View by lazy {
        LayoutInflater.from(context).inflate(R.layout.add_ingredient_to_recipe_dialog, null)
    }
    private val amountEditText: EditText by lazy {
        contentView.findViewById(R.id.IngredientItemAmountEditText)
    }
    private val ingredientSpinner: Spinner by lazy {
        contentView.findViewById(R.id.IngredientItemSpinner)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        ingredientSpinner.apply {
            adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                ingredients.map {
                    it.ingredientName
                }
            )
        }

        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.add_existing_ingredient)
            .setView(contentView)
            .setPositiveButton(R.string.ok) { _, _ ->
                if (isValid) {
                    listener.onItemCreated(ingredientWithAmount)
                }
            }
            .setNegativeButton(R.string.cancel, null)
            .create()
    }

    private val isValid
        get() = amountEditText.text.isNotBlank() && amountEditText.text.toString().toDoubleOrNull() != null

    private val ingredientWithAmount: IngredientWithAmount
        get() = IngredientWithAmount(
            ingredient = ingredients[ingredientSpinner.selectedItemPosition],
            amount = amountEditText.text.toString().toDouble()
        )
}
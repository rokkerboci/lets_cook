package com.triad.school.letscook.fragments.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.triad.school.letscook.R
import com.triad.school.letscook.dto.Recipe
import com.triad.school.letscook.fragments.CreateItemListener

class CreateRecipeDialogFragment(
    private val listener: CreateItemListener<Recipe>
) : DialogFragment() {

    private val contentView: View by lazy {
        LayoutInflater.from(context).inflate(R.layout.create_recipe_dialog, null)
    }
    private val nameEditText: EditText by lazy {
        contentView.findViewById(R.id.RecipeItemNameEditText)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.create_new_recipe)
            .setView(contentView)
            .setPositiveButton(R.string.ok) { _, _ ->
                if (isValid) {
                    listener.onItemCreated(recipe)
                }
            }
            .setNegativeButton(R.string.cancel, null)
            .create()
    }

    private val isValid
        get() = nameEditText.text.isNotBlank()

    private val recipe: Recipe
        get() = Recipe(
            recipeId = 0,
            recipeName = nameEditText.text.toString(),
            recipeDescription = ""
        )
}
package com.triad.school.letscook.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.triad.school.letscook.R
import com.triad.school.letscook.adapter.IngredientWithAmountAdapter
import com.triad.school.letscook.dto.Ingredient
import com.triad.school.letscook.dto.Recipe
import com.triad.school.letscook.dto.RecipeIngredientCrossRef
import com.triad.school.letscook.fragments.dialogs.AddIngredientToRecipeDialogFragment
import com.triad.school.letscook.storage.IngredientWithAmount
import com.triad.school.letscook.storage.RecipeDatabase
import kotlinx.android.synthetic.main.edit_recipe.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditRecipeFragment : Fragment() {
    private val database: RecipeDatabase by lazy {
        RecipeDatabase.getInstance(requireContext())
    }

    private lateinit var ingredients: List<Ingredient>
    private val args: EditRecipeFragmentArgs by navArgs()
    private val ingredientWithAmountAdapter = IngredientWithAmountAdapter(this::removeItem)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        lifecycleScope.launch(Dispatchers.Main) {
            ingredients = withContext(Dispatchers.IO) {
                database.ingredientDao().getAll()
            }

            val items = withContext(Dispatchers.IO) {
                database.recipeIngredientCrossRefDao().getIngredients(args.recipeId)
            }

            ingredientWithAmountAdapter.setItems(items)
        }

        return inflater.inflate(R.layout.edit_recipe, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ingredientWithAmountListRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = ingredientWithAmountAdapter
        }

        lifecycleScope.launch(Dispatchers.Main) {
            val item = withContext(Dispatchers.IO) {
                database.recipeDao().getFromId(args.recipeId)
            }

            RecipeItemNameEditText.setText(item.recipeName)
            RecipeItemDescriptionEditText.setText(item.recipeDescription)
        }

        save_recipe_button.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                database.recipeDao().update(
                    Recipe(
                        recipeId = args.recipeId,
                        recipeName = RecipeItemNameEditText.text.toString(),
                        recipeDescription = RecipeItemDescriptionEditText.text.toString()
                    )
                )
            }

            findNavController().navigateUp()
        }

        fab.setOnClickListener {
            if (ingredients.isEmpty()) {
                Toast.makeText(context, "No ingredients", Toast.LENGTH_LONG).show()
            } else {
                AddIngredientToRecipeDialogFragment(ingredients) {
                    addItem(it)
                }.show(
                    childFragmentManager,
                    "Add ingredient to recipe"
                )
            }
        }
    }

    private fun addItem(ingredient: IngredientWithAmount) {
        ingredientWithAmountAdapter.addItem(ingredient)

        lifecycleScope.launch(Dispatchers.IO) {
            val item = RecipeIngredientCrossRef(
                args.recipeId,
                ingredient.ingredient.ingredientId,
                ingredient.amount
            )

            database.recipeIngredientCrossRefDao().insert(item)
        }
    }

    private fun removeItem(ingredient: IngredientWithAmount)  {
        ingredientWithAmountAdapter.removeItem(ingredient)

        lifecycleScope.launch(Dispatchers.IO) {
            val item = RecipeIngredientCrossRef(
                args.recipeId,
                ingredient.ingredient.ingredientId,
                ingredient.amount
            )

            database.recipeIngredientCrossRefDao().delete(item)
        }
    }
}
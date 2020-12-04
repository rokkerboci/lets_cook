package com.triad.school.letscook.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.triad.school.letscook.R
import com.triad.school.letscook.adapter.IngredientWithAmountForShowMenuAdapter
import com.triad.school.letscook.dto.Ingredient
import com.triad.school.letscook.dto.IngredientWithAmount
import com.triad.school.letscook.storage.RecipeDatabase
import kotlinx.android.synthetic.main.edit_recipe.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ShowMenuIngredientsFragment : Fragment() {
    private val database: RecipeDatabase by lazy {
        RecipeDatabase.getInstance(requireContext())
    }

    private val args: ShowMenuIngredientsFragmentArgs by navArgs()
    private val ingredientWithAmountAdapter = IngredientWithAmountForShowMenuAdapter()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.show_menu_ingredients, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ingredientWithAmountListRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = ingredientWithAmountAdapter
        }

        lifecycleScope.launch(Dispatchers.IO) {
            val ingredients = mutableMapOf<Ingredient, Double>()

            args.recipeDescriptors.forEach { descriptor ->
                val items = database.recipeIngredientCrossRefDao().getIngredients(descriptor.recipeId)

                items.forEach { ingredient ->
                    ingredients.computeIfAbsent(ingredient.ingredient) { 0.0 }
                    val current = ingredients[ingredient.ingredient]!!
                    ingredients[ingredient.ingredient] = current + descriptor.amount * ingredient.amount
                }
            }

            withContext(Dispatchers.Main) {
                ingredientWithAmountAdapter.setItems(ingredients.map {
                    IngredientWithAmount(it.key, it.value)
                })
            }
        }
    }
}
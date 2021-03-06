package com.triad.school.letscook.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.triad.school.letscook.R
import com.triad.school.letscook.adapter.IngredientAdapter
import com.triad.school.letscook.dto.Ingredient
import com.triad.school.letscook.fragments.dialogs.CreateIngredientDialogFragment
import com.triad.school.letscook.storage.RecipeDatabase
import kotlinx.android.synthetic.main.ingredient_list.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class IngredientListFragment : Fragment() {
    private val database: RecipeDatabase by lazy {
        RecipeDatabase.getInstance(requireContext())
    }

    private val ingredientAdapter = IngredientAdapter(this::removeItem)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        lifecycleScope.launch(Dispatchers.Main) {
            val ingredients = withContext(Dispatchers.IO) {
                database.ingredientDao().getAll()
            }

            ingredientAdapter.setItems(ingredients)
        }

        return inflater.inflate(R.layout.ingredient_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ingredientListRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = ingredientAdapter
        }

        fab.setOnClickListener {
            CreateIngredientDialogFragment {
                addItem(it)
            }.show(
                childFragmentManager,
                "New ingredient"
            )
        }
    }

    private fun removeItem(ingredient: Ingredient) {
        ingredientAdapter.removeItem(ingredient)

        lifecycleScope.launch(Dispatchers.IO) {
            database.ingredientDao().delete(ingredient)
        }
    }

    private fun addItem(ingredient: Ingredient) {
        lifecycleScope.launch(Dispatchers.IO) {
            val id = database.ingredientDao().insert(ingredient)

            withContext(Dispatchers.Main) {
                ingredientAdapter.addItem(ingredient.copy(ingredientId = id))
            }
        }
    }
}
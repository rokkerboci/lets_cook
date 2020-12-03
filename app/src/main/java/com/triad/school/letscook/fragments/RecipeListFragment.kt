package com.triad.school.letscook.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.triad.school.letscook.R
import com.triad.school.letscook.adapter.RecipeAdapter
import com.triad.school.letscook.dto.Recipe
import com.triad.school.letscook.fragments.dialogs.CreateRecipeDialogFragment
import com.triad.school.letscook.storage.RecipeDatabase
import kotlinx.android.synthetic.main.ingredient_list.fab
import kotlinx.android.synthetic.main.recipe_list.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RecipeListFragment : Fragment() {
    private val database: RecipeDatabase by lazy {
        RecipeDatabase.getInstance(requireContext())
    }

    private val recipeAdapter = RecipeAdapter(this::removeItem, this::editItem)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        lifecycleScope.launch(Dispatchers.Main) {
            val recipe = withContext(Dispatchers.IO) {
                database.recipeDao().getAll()
            }

            recipeAdapter.setItems(recipe)
        }

        return inflater.inflate(R.layout.recipe_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recipeListRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = recipeAdapter
        }

        fab.setOnClickListener {
            CreateRecipeDialogFragment {
                addItem(it)
            }.show(
                childFragmentManager,
                "New recipe"
            )
        }
    }

    private fun removeItem(recipe: Recipe) {
        recipeAdapter.removeItem(recipe)

        lifecycleScope.launch(Dispatchers.IO) {
            database.recipeDao().delete(recipe)
        }
    }

    private fun addItem(recipe: Recipe) {
        lifecycleScope.launch(Dispatchers.IO) {
            val id = database.recipeDao().insert(recipe)

            withContext(Dispatchers.Main) {
                recipeAdapter.addItem(recipe.copy(recipeId = id))
            }
        }
    }

    private fun editItem(recipe: Recipe) {
        findNavController().navigate(RecipeListFragmentDirections.actionRecipeListToEditRecipe(recipe.recipeId))
    }
}
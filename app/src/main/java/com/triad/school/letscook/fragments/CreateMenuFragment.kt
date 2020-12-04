package com.triad.school.letscook.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.triad.school.letscook.R
import com.triad.school.letscook.adapter.CreateMenuAdapter
import com.triad.school.letscook.dto.Recipe
import com.triad.school.letscook.dto.RecipeWithAmount
import com.triad.school.letscook.dto.RecipeWithAmountDescriptor
import com.triad.school.letscook.fragments.dialogs.AddRecipeToMenuDialogFragment
import com.triad.school.letscook.storage.RecipeDatabase
import kotlinx.android.synthetic.main.create_menu.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CreateMenuFragment : Fragment() {
    private val database: RecipeDatabase by lazy {
        RecipeDatabase.getInstance(requireContext())
    }

    private val addedRecipes = mutableListOf<RecipeWithAmount>()

    private lateinit var recipes: List<Recipe>

    private val createMenuAdapter = CreateMenuAdapter(this::removeItem)

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.create_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch(Dispatchers.Main) {
            recipes = withContext(Dispatchers.IO) {
                database.recipeDao().getAll()
            }
        }

        recipeWithAmountListRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = createMenuAdapter
        }

        fab.setOnClickListener {
            if (recipes.isEmpty()) {
                Toast.makeText(context, "No recipes. First, add some!", Toast.LENGTH_LONG).show()
            } else {
                AddRecipeToMenuDialogFragment(recipes) {
                    addItem(it)
                }.show(
                    childFragmentManager,
                    "asdfasdf"
                )
            }
        }

        button.setOnClickListener {
            findNavController().navigate(
                CreateMenuFragmentDirections.actionRecipeListToEditRecipe(addedRecipes.map {
                    RecipeWithAmountDescriptor(it.recipe.recipeId, it.amount)
                }.toTypedArray())
            )
        }
    }

    fun addItem(recipeWithAmount: RecipeWithAmount) {
        addedRecipes.find { it.recipe.recipeId == recipeWithAmount.recipe.recipeId }?.let {
            removeItem(it)
        }

        addedRecipes.add(recipeWithAmount)
        createMenuAdapter.addItem(recipeWithAmount)

        start_text.isVisible = false
    }

    fun removeItem(recipeWithAmount: RecipeWithAmount) {
        addedRecipes.remove(recipeWithAmount)
        createMenuAdapter.removeItem(recipeWithAmount)

        if (createMenuAdapter.itemCount == 0) {
            start_text.isVisible = true
        }
    }

}
package com.triad.school.letscook.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.triad.school.letscook.R
import com.triad.school.letscook.adapter.IngredientAdapter
import com.triad.school.letscook.storage.RecipeDatabase
import kotlinx.android.synthetic.main.ingredient_list.*
import kotlinx.coroutines.*

class IngredientListFragment : Fragment() {
    private lateinit var database: RecipeDatabase

    private val ingredientAdapter = IngredientAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        database = RecipeDatabase.getInstance(requireContext())

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
            NewIngredientDialogFragment {
                ingredientAdapter.addItem(it)
            }.show(
                childFragmentManager,
                "New ingredient"
            )
        }
    }
}
package com.triad.school.letscook.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.triad.school.letscook.R
import kotlinx.android.synthetic.main.main_menu.*

class MainMenuFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.main_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        button_first.setOnClickListener {
            findNavController().navigate(MainMenuFragmentDirections.actionMainMenuToIngredientList())
        }
        button_second.setOnClickListener {
            findNavController().navigate(MainMenuFragmentDirections.actionMainMenuToRecipeList())
        }
    }

}
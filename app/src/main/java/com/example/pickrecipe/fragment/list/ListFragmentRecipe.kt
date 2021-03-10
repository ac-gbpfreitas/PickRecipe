package com.example.pickrecipe.fragment.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pickrecipe.R
import com.example.pickrecipe.adapters.RecipeAdapter
import com.example.pickrecipe.model.User
import com.example.pickrecipe.viewmodel.RecipeViewModel
import kotlinx.android.synthetic.main.fragment_list_recipe.view.*

class ListFragmentRecipe : Fragment() {

    private lateinit var mRecipeViewModel : RecipeViewModel;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Dumb variables for test
        //Dumb user
        var userTest = User();

        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_list_recipe, container, false);


        var recipeAdapter = RecipeAdapter();
        val recyclerView = view.recyclerViewList;
        recyclerView.adapter = recipeAdapter;
        recyclerView.layoutManager = LinearLayoutManager(requireContext());

        mRecipeViewModel = ViewModelProvider(this).get(RecipeViewModel::class.java);
        mRecipeViewModel.addAllRecipes();
        mRecipeViewModel.readAllRecipes.observe(viewLifecycleOwner, { 
                recipe -> recipeAdapter.setData(recipe);
        })

        setHasOptionsMenu(true);
        return view;
    }

}
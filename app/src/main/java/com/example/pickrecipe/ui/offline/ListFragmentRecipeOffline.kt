package com.example.pickrecipe.ui.offline

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pickrecipe.R
import com.example.pickrecipe.adapters.RecipeAdapter
import com.example.pickrecipe.unused.User
import com.example.pickrecipe.viewmodel.RecipeViewModel
import kotlinx.android.synthetic.main.fragment_list_recipe.view.*

class ListFragmentRecipeOffline : Fragment() {

    private lateinit var mRecipeViewModel : RecipeViewModel;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_list_recipe, container, false);


        var recipeAdapter = RecipeAdapter();
        val recyclerView = view.recyclerViewList;
        recyclerView.adapter = recipeAdapter;
        recyclerView.layoutManager = LinearLayoutManager(requireContext());

        mRecipeViewModel = ViewModelProvider(this).get(RecipeViewModel::class.java);
        mRecipeViewModel.readAllRecipes.observe(viewLifecycleOwner, {
                recipe -> recipeAdapter.setData(recipe);
        })

        setHasOptionsMenu(true);
        return view;
    }


}
package com.example.pickrecipe.fragment.display

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pickrecipe.R
import com.example.pickrecipe.adapters.DetailRecipeAdapter
import com.example.pickrecipe.db.model.RecipeEntity
import com.example.pickrecipe.model.Ingredient
import com.example.pickrecipe.model.Recipe
import com.example.pickrecipe.viewmodel.RecipeViewModel
import kotlinx.android.synthetic.main.fragment_details_recipe.view.*


class DetailsFragmentRecipe : Fragment() {



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_details_recipe, container, false);

        var mRecipeViewModel : RecipeViewModel = ViewModelProvider(this).get(RecipeViewModel::class.java);
        
        var id = arguments?.getString("id");
        var title = arguments?.getString("title");
        var rating = arguments?.getString("rating");
        var details = arguments?.getString("details");
        var picture = arguments?.getString("picture");
        var directions = arguments?.getString("directions");
        var ingredients = arguments?.getString("ingredients");
        var comments = arguments?.getString("comments");


        var recipeDetail = Recipe(
                id!!,title!!,details!!,directions!!,rating!!.toDouble(),picture!!,ingredients!!,comments!!
        );


        var recipeDetailAdapter = DetailRecipeAdapter();
        recipeDetailAdapter.setData(recipeDetail);

        val recyclerView = view.recyclerViewDetail;
        recyclerView.adapter = recipeDetailAdapter;
        recyclerView.layoutManager = LinearLayoutManager(requireContext());

        setHasOptionsMenu(true);
        return view;
    }
}
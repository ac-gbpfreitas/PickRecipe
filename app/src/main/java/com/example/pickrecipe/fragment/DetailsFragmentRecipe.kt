package com.example.pickrecipe.fragment

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
    private lateinit var mRecipeViewModel : RecipeViewModel;

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_details_recipe, container, false);

        
        var id = arguments?.getString("id");
        var title = arguments?.getString("title");
        var rating = arguments?.getString("rating");
        var details = arguments?.getString("details");
        var picture = arguments?.getString("picture");
        var directions = arguments?.getString("directions");

        Log.d("RECIPE",id.toString());
//        Log.d("recipe",title.toString());
//        Log.d("recipe",rating.toString());
//        Log.d("recipe",details.toString());
//        Log.d("recipe",picture.toString());
//        Log.d("recipe",directions.toString());

        var recipeDetail = RecipeEntity(
                id!!,title!!, rating!!.toDouble(),details!!,directions!!,picture!!
        );


        var mRecipeViewModel = ViewModelProvider(this).get(RecipeViewModel::class.java);
        mRecipeViewModel.getIngredientsByRecipeId(id);

        var ingredients : ArrayList<Ingredient> = arrayListOf();
        for(newIngredient in mRecipeViewModel.ingredientsFromRecipe){
            ingredients.add(
                Ingredient(
                    newIngredient.ingredientId,
                    newIngredient.recipeId,
                    newIngredient.detail,
                    newIngredient.unity,
                    newIngredient.quantity,
                    newIngredient.glutenFree
                )
            );
        }

        var recipeDetail2 = Recipe(
                recipeDetail.recipeId,
                recipeDetail.recipeTitle,
                recipeDetail.details,
                recipeDetail.directions,
                recipeDetail.rating,
                recipeDetail.picture,
                ingredients
                );

        var recipeDetailAdapter = DetailRecipeAdapter();

        recipeDetailAdapter.setData(recipeDetail2);

        val recyclerView = view.recyclerViewDetail;
        recyclerView.adapter = recipeDetailAdapter;
        recyclerView.layoutManager = LinearLayoutManager(requireContext());

        setHasOptionsMenu(true);
        return view;
    }
}
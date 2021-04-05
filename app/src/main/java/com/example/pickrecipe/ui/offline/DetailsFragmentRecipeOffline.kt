package com.example.pickrecipe.ui.offline

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pickrecipe.R
import com.example.pickrecipe.adapters.DetailRecipeAdapter
import com.example.pickrecipe.model.Recipe
import kotlinx.android.synthetic.main.fragment_details_recipe_offline.view.*


class DetailsFragmentRecipeOffline : Fragment(), DetailRecipeAdapter.ListItemListener {


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
        var ingredients = arguments?.getString("ingredients");
        var comments = arguments?.getString("comments");
        var tags = arguments?.getString("tags");

        var pantryCheck = "You are in offline mode. Please login for pantry check."

        var recipeDetail = Recipe(
                id!!,title!!,details!!,directions!!,rating!!.toDouble(),picture!!,ingredients!!,comments!!,pantryCheck,
            false,tags!!
        );


        var recipeDetailAdapter = DetailRecipeAdapter(this@DetailsFragmentRecipeOffline,true);
        recipeDetailAdapter.setData(recipeDetail);

        val recyclerView = view.recyclerViewDetail;
        recyclerView.adapter = recipeDetailAdapter;
        recyclerView.layoutManager = LinearLayoutManager(requireContext());

        setHasOptionsMenu(true);
        return view;
    }

    override fun submitComment(comment: String, userRating: Double) {
    }

    override fun updateRating(newRating: Double) {
    }

    override fun addFavorite(id: String) {
        TODO("Not yet implemented")
    }

    override fun removeFavorite(id: String) {
        TODO("Not yet implemented")
    }

}
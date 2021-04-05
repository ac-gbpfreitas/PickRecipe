package com.example.pickrecipe.unused

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.pickrecipe.R
import com.example.pickrecipe.model.Recipe

class DetailRecipeAdapterTester(
    /*private var recipe : Recipe*/
    ) : RecyclerView.Adapter<DetailRecipeAdapterTester.DetailRecipeViewHolder>() {

    lateinit var currentRecipe : Recipe;

    inner class DetailRecipeViewHolder(@NonNull itemView : View) : RecyclerView.ViewHolder(itemView){
        lateinit var textTitle   : TextView;
        lateinit var textRating  : TextView;
        lateinit var textIngredients : TextView;
        lateinit var textDirections : TextView;
        lateinit var imageRecipe : ImageView;
        lateinit var imageStar   : ImageView;
        lateinit var btnBack : Button;
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailRecipeViewHolder {
        var itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.recycler_recipe_detail,
            parent,
            false
        );

        var detailHolder = DetailRecipeViewHolder(itemView);

        detailHolder.imageStar = itemView.findViewById(R.id.imageStarDetails);
        detailHolder.imageRecipe = itemView.findViewById(R.id.imageRecipeDetails);
//        detailHolder.textTitle = itemView.findViewById(R.id.cu);
        detailHolder.textIngredients = itemView.findViewById(R.id.textIngredientDetails);
        detailHolder.textRating = itemView.findViewById(R.id.textTitleDetails);
        detailHolder.textDirections = itemView.findViewById(R.id.textDirectionsDetails);
        detailHolder.btnBack = itemView.findViewById(R.id.btnBackDetail);


        detailHolder.imageStar.setOnClickListener {
            if(detailHolder.imageStar.alpha == 0.25f){
                detailHolder.imageStar.alpha = 0.75f
            } else {
                detailHolder.imageStar.alpha = 0.25f
            }
        }

        detailHolder.btnBack.setOnClickListener {
            detailHolder.itemView.findNavController().navigate(R.id.ListFragment);
        }

        return detailHolder;
    }

    override fun onBindViewHolder(holder: DetailRecipeViewHolder, position: Int) {
        var currentItem = this.currentRecipe;



        var textIngredients : String = "";

        this.currentRecipe.getIngredients();

        holder.textIngredients.text = textIngredients;
        Log.d("INGREDIENTS",textIngredients);
    }

    override fun getItemCount(): Int {
        return 1;
    }

    fun setData(newRecipe : Recipe){
        this.currentRecipe = newRecipe;
        notifyDataSetChanged();
    }
}
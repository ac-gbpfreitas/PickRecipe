package com.example.pickrecipe.adapters

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
import com.squareup.picasso.Picasso

class DetailRecipeAdapter(

    ) : RecyclerView.Adapter<DetailRecipeAdapter.DetailRecipeViewHolder>() {


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
        detailHolder.textTitle = itemView.findViewById(R.id.textRecipeTitleDetails);
        detailHolder.textIngredients = itemView.findViewById(R.id.textIngredientDetails);
        detailHolder.textRating = itemView.findViewById(R.id.textRatingDetails);
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
//            detailHolder.itemView.findNavController().navigate(R.id.ListFragment);
            detailHolder.itemView.findNavController().navigate(R.id.nav_home);
        }

        return detailHolder;
    }

    override fun onBindViewHolder(holder: DetailRecipeViewHolder, position: Int) {
        //var currentItem = this.currentRecipe;
        holder.textTitle.text = this.currentRecipe.getRecipeTitle();
        holder.textRating.text = "Rating: "+this.currentRecipe.getRate();
        holder.textDirections.text = this.currentRecipe.getDirections();

        //Parse Ingredients String
        var ingredientList = this.currentRecipe.getIngredients().split("|");

        var textIngredient : String = "";
        //for((j,ingredientLine) in ingredientList.withIndex()){
        for((j,ingredientLine) in ingredientList.withIndex()){
            /*
            //I've add the ingredient id in the string, just in case
            //That is why the condition below avoid inserting the ingredient id
            */
            if(!ingredientList[j].contains("#id")){
                textIngredient += ingredientLine+" ";
            }
        }
        holder.textIngredients.text = textIngredient;

        if(currentRecipe.getPicture() == ""){
            holder.imageRecipe.setImageResource(R.drawable.food);
        } else {
            Picasso.get().load(currentRecipe.getPicture()).into(holder.imageRecipe);
        }

    }

    override fun getItemCount(): Int {
        return 1;
    }

    fun setData(newRecipe : Recipe){
        this.currentRecipe = newRecipe;
        notifyDataSetChanged();
    }
}
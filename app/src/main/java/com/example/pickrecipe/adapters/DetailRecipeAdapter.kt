package com.example.pickrecipe.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.pickrecipe.R
import com.example.pickrecipe.db.model.IngredientEntity
import com.example.pickrecipe.db.model.RecipeEntity
import com.example.pickrecipe.model.Ingredient
import com.example.pickrecipe.model.Recipe
import com.squareup.picasso.Picasso

class DetailRecipeAdapter(
    /*private var recipe : Recipe*/
    ) : RecyclerView.Adapter<DetailRecipeAdapter.DetailRecipeViewHolder>() {

    //var recipeList = emptyList<RecipeEntity>();
    lateinit var currentRecipe : Recipe;

    inner class DetailRecipeViewHolder(@NonNull itemView : View) : RecyclerView.ViewHolder(itemView){
        lateinit var textTitle   : TextView;
        lateinit var textRating  : TextView;
        lateinit var textIngredients : TextView;
        lateinit var textDirections : TextView;
        lateinit var imageRecipe : ImageView;
        lateinit var imageStar   : ImageView;
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
        detailHolder.textTitle = itemView.findViewById(R.id.textRecipeTileDetails);
        detailHolder.textIngredients = itemView.findViewById(R.id.textIngredientDetails);
        detailHolder.textRating = itemView.findViewById(R.id.textRatingDetails);
        detailHolder.textDirections = itemView.findViewById(R.id.textDirectionsDetails);


        detailHolder.imageStar.setOnClickListener {
            if(detailHolder.imageStar.alpha == 0.25f){
                detailHolder.imageStar.alpha = 0.75f
            } else {
                detailHolder.imageStar.alpha = 0.25f
            }
        }

        return detailHolder;
    }

    override fun onBindViewHolder(holder: DetailRecipeViewHolder, position: Int) {
        var currentItem = this.currentRecipe;
        holder.textTitle.text = this.currentRecipe.getRecipeTitle();
        holder.textRating.text = "Rating: "+this.currentRecipe.getRate();
        if(currentItem.getPicture() == ""){
            holder.imageRecipe.setImageResource(R.drawable.food);
        } else {
            Picasso.get().load(currentItem.getPicture()).into(holder.imageRecipe);
        }

        var textIngredients : String = "";
        for(ingredient in this.currentRecipe.getIngredients()){
            textIngredients += "#" + ingredient.getQuantity();
            textIngredients += " " + ingredient.getUnity();
            textIngredients += " " + ingredient.getIngredientDescription();
            textIngredients += "\n";
        }
        holder.textIngredients.text = textIngredients;
        Log.d("INGREDIENTS",textIngredients);

        holder.textDirections.text = this.currentRecipe.getDirections();
    }

    override fun getItemCount(): Int {
        return 1;
    }

    fun setData(newRecipe : Recipe){
        this.currentRecipe = newRecipe;
        notifyDataSetChanged();
    }
}
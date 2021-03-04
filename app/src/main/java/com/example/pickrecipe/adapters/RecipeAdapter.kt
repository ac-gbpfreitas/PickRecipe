package com.example.pickrecipe.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.pickrecipe.R
import com.example.pickrecipe.model.Recipe
import com.example.pickrecipe.model.User
import kotlinx.android.synthetic.main.recycler_recipe_list.view.*

class RecipeAdapter : RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>{

    var recipeList = emptyList<Recipe>();
    lateinit var currentUser : User;
    var context : Context;

    constructor(newList : List<Recipe>, newContext : Context){
        this.context = newContext;
        this.recipeList = newList;
    }

    inner class RecipeViewHolder(@NonNull itemView : View) : RecyclerView.ViewHolder(itemView){
        lateinit var textTitle   : TextView;
        lateinit var textRating  : TextView;
        lateinit var textDetails : TextView;
        lateinit var imageRecipe : ImageView;
        lateinit var imageStar   : ImageView;
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        var itemView : View = LayoutInflater.from(parent.context).inflate(
            R.layout.recycler_recipe_list,
            parent,
            false
        );

        var recipeHolder = RecipeViewHolder(itemView);

        recipeHolder.textTitle   = itemView.textViewTitle;
        recipeHolder.textDetails = itemView.textViewDetails;
        recipeHolder.textRating  = itemView.textViewRating;
        recipeHolder.imageRecipe = itemView.imageViewDish;
        recipeHolder.imageStar   = itemView.imageViewStar;

        return recipeHolder;
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.textTitle.text = this.recipeList[position].getRecipeTitle();
        holder.textDetails.text = this.recipeList[position].getDetails();
        holder.textRating.text = this.recipeList[position].getRate().toString();
        holder.imageStar.setOnClickListener {
            holder.imageStar.alpha = 0.75f;
            this.currentUser.getBookMarksRecipe().add(this.recipeList[position]);
        }
    }

    override fun getItemCount(): Int {
        return this.recipeList.size;
    }

    fun setData(newUser : User, newList : List<Recipe>){
        this.currentUser = newUser;
        this.recipeList = newList;
        notifyDataSetChanged();
    }

}
package com.example.pickrecipe.adapters

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.pickrecipe.R
import com.example.pickrecipe.db.model.RecipeEntity
import com.example.pickrecipe.model.User
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.recycler_recipe_list.view.*

class RecipeAdapter () : RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>(){

    var recipeList = emptyList<RecipeEntity>();
    lateinit var currentUser : User;

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
//        recipeHolder.imageStar   = itemView.imageViewStar;

        return recipeHolder;
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        var currentItem = this.recipeList[position];
        holder.textTitle.text = this.recipeList[position].recipeTitle;
        holder.textDetails.text = this.recipeList[position].details;
        holder.textRating.text = "Rating: "+this.recipeList[position].rating.toString();

        val bundle = bundleOf(
            "id" to this.recipeList[position].recipeId,
            "title" to this.recipeList[position].recipeTitle,
            "rating" to this.recipeList[position].rating.toString(),
            "details" to this.recipeList[position].details,
            "directions" to this.recipeList[position].directions,
            "picture" to this.recipeList[position].picture,
            "ingredients" to this.recipeList[position].ingredients,
            "comments" to this.recipeList[position].comments,
            "tags" to this.recipeList[position].tags
        );

        holder.itemView.recipeList.setOnClickListener {
            holder.itemView.findNavController().navigate(R.id.DetailsFragmentRecipe,bundle);
        }

        if(recipeList[position].picture == ""){
            holder.imageRecipe.setImageResource(R.drawable.food);
        } else {
            Picasso.get().load(recipeList[position].picture).into(holder.imageRecipe);
        }

//        holder.imageStar.setOnClickListener {
//            if(holder.imageStar.alpha != 0.75f){
//                holder.imageStar.alpha = 0.75f;
//                listener.addFavorite(recipeList[position].recipeId)
//            } else {
//                holder.imageStar.alpha = 0.25f;
//                listener.removeFavorite(recipeList[position].recipeId)
//            }
//        }
    }

    override fun getItemCount(): Int {
        return this.recipeList.size;
    }

    fun setData(/*newUser : User,*/ newList : List<RecipeEntity>){
        //this.currentUser = newUser;
        this.recipeList = newList;
        notifyDataSetChanged();
    }


}
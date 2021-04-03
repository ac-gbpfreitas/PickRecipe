package com.example.pickrecipe.adapters

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
import com.example.pickrecipe.unused.User
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.recycler_recipe_list.view.*
import java.text.DecimalFormat

class RecipeAdapter () : RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>(){

    var recipeList = emptyList<RecipeEntity>();

    inner class RecipeViewHolder(@NonNull itemView : View) : RecyclerView.ViewHolder(itemView){
        lateinit var textTitle   : TextView;
        lateinit var textRating  : TextView;
        lateinit var textDetails : TextView;
        lateinit var imageRecipe : ImageView;
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

        return recipeHolder;
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.textTitle.text = this.recipeList[position].recipeTitle;
        holder.textDetails.text = this.recipeList[position].details;

        val df = DecimalFormat("#.##")
        holder.textRating.text = "Rating: ${df.format(this.recipeList[position].rating)}";

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
    }

    override fun getItemCount(): Int {
        return this.recipeList.size;
    }

    fun setData(newList : List<RecipeEntity>){
        this.recipeList = newList;
        notifyDataSetChanged();
    }


}
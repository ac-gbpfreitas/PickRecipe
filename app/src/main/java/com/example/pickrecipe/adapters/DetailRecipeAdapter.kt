package com.example.pickrecipe.adapters

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.NonNull
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.pickrecipe.R
import com.example.pickrecipe.model.Recipe
import com.squareup.picasso.Picasso
import java.text.DecimalFormat

class DetailRecipeAdapter( private val listener: DetailRecipeAdapter.ListItemListener, private val offlineMode : Boolean

    ) : RecyclerView.Adapter<DetailRecipeAdapter.DetailRecipeViewHolder>() {

    lateinit var currentRecipe : Recipe;

    inner class DetailRecipeViewHolder(@NonNull itemView : View) : RecyclerView.ViewHolder(itemView){
        lateinit var textTitle   : TextView;
        lateinit var textRating  : TextView;
        lateinit var textIngredients : TextView;
        lateinit var textDirections : TextView;
        lateinit var textComments : TextView;
        lateinit var textPantryMatch : TextView;
        lateinit var textTags : TextView;
        lateinit var imageRecipe : ImageView;
        lateinit var imageStar   : ImageView;
        lateinit var btnBack : Button;
        lateinit var btnSubmit : Button;
        lateinit var editTextComment : EditText;
        lateinit var spinnerRating : Spinner
        lateinit var commentBlock : LinearLayout
    }

    interface ListItemListener {
        fun submitComment(comment : String, userRating: Double)
        fun updateRating (newRating : Double)
        fun addFavorite(id : String)
        fun removeFavorite (id : String)
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
        detailHolder.textTitle = itemView.findViewById(R.id.textTitleDetails);
        detailHolder.textIngredients = itemView.findViewById(R.id.textIngredientDetails);
        detailHolder.textRating = itemView.findViewById(R.id.textRatingDetails);
        detailHolder.textDirections = itemView.findViewById(R.id.textDirectionsDetails);
        detailHolder.btnBack = itemView.findViewById(R.id.btnBackDetail);
        detailHolder.textComments = itemView.findViewById(R.id.textCommentsDetails);
        detailHolder.btnSubmit = itemView.findViewById(R.id.btnSubmitComment);
        detailHolder.editTextComment = itemView.findViewById(R.id.editTextLeaveAComment)
        detailHolder.spinnerRating = itemView.findViewById(R.id.spinnerRatingScores)
        detailHolder.textPantryMatch = itemView.findViewById(R.id.textIngredientPantryMatch)
        detailHolder.commentBlock = itemView.findViewById(R.id.commentBlock)
        detailHolder.textTags = itemView.findViewById(R.id.textTagsDetails)


        detailHolder.imageStar.setOnClickListener {
            if(detailHolder.imageStar.alpha != 0.75f){
                detailHolder.imageStar.alpha = 0.75f;
                listener.addFavorite(currentRecipe.getRecipeId())
            } else {
                detailHolder.imageStar.alpha = 0.25f;
                listener.removeFavorite(currentRecipe.getRecipeId())
            }
        }

        detailHolder.btnBack.setOnClickListener {

            if (offlineMode) {
                detailHolder.itemView.findNavController().navigate(R.id.ListFragment);
            } else {
                detailHolder.itemView.findNavController().navigate(R.id.nav_home);
            }
        }

        detailHolder.btnSubmit.setOnClickListener {
            var comment = detailHolder.editTextComment.text.toString()
            val rating = detailHolder.spinnerRating.selectedItemPosition.toDouble()
            if (comment == "") comment = "Empty comment"
            listener.submitComment(comment,rating)

            val newRating: Double
            val numberOfComments = currentRecipe.getRecipeComments().split("|").size - 1
            val sum = currentRecipe.getRating() * numberOfComments
            newRating = (rating + sum) / (numberOfComments + 1)
            Log.d("RATING","$numberOfComments, $sum, $newRating")

            listener.updateRating(newRating)

            detailHolder.editTextComment.setText("")

        }

        if (currentRecipe.getPantryCheck() == "") detailHolder.textPantryMatch.text = "Loading Pantry results..."
        else {
            detailHolder.textPantryMatch.text = currentRecipe.getPantryCheck()
        }

        return detailHolder;
    }

    override fun onBindViewHolder(holder: DetailRecipeViewHolder, position: Int) {
        holder.textTitle.text = this.currentRecipe.getRecipeTitle();
        holder.textTags.text = this.currentRecipe.getTags()



        val df = DecimalFormat("#.##")
        holder.textRating.text = "Rating: ${df.format(this.currentRecipe.getRating())}";

        var directionsSteps = this.currentRecipe.getDirections().split(".")
        var directionsParsed = "Directions:\n"
        for (directionsStep in directionsSteps) {
            if (directionsStep != "") {
                directionsParsed += "➤ ${directionsStep.trim()}.\n"
            }
        }
        holder.textDirections.text = directionsParsed

        //Parse Ingredients String
        var ingredientList = this.currentRecipe.getIngredients().split("|");

        var textIngredient: String = "";
        //for((j,ingredientLine) in ingredientList.withIndex()){
        for ((j, ingredientLine) in ingredientList.withIndex()) {
            /*
            //I've add the ingredient id in the string, just in case
            //That is why the condition below avoid inserting the ingredient id
            */
            if (!ingredientList[j].contains("#id")) {
                textIngredient += ingredientLine + "\n";

                var textIngredient: String = "Ingredients:\n";
                for (ingredientLine in ingredientList) {
                    if (ingredientLine != "") {
                        textIngredient += "${ingredientLine.trim().capitalize()}\n";
                    }
                }
                holder.textIngredients.text = textIngredient;

                if (currentRecipe.getPicture() == "") {
                    holder.imageRecipe.setImageResource(R.drawable.food);
                } else {
                    Picasso.get().load(currentRecipe.getPicture()).into(holder.imageRecipe);
                }

                if (holder.textPantryMatch.text != "You're all set! You have every ingredient of this recipe in your pantry.") {
                    holder.textPantryMatch.setTextColor(Color.RED)
                } else {
                    holder.textPantryMatch.setTextColor(Color.GREEN)
                }

                if (currentRecipe.getRecipeComments() == "") {
                    holder.textComments.text = "No comments yet."
                } else {
                    holder.textComments.text = parseAndDisplayComments(currentRecipe.getRecipeComments())
                }

                if (currentRecipe.getIsFavorite()) holder.imageStar.alpha = 0.75f;
                else holder.imageStar.alpha = 0.25f;

                if (offlineMode) {
                    holder.commentBlock.isVisible = false
                    holder.btnSubmit.isVisible = false
                    holder.imageStar.isVisible = false
                }

            }
        }
    }

    private fun parseAndDisplayComments(cooments: String): CharSequence? {
        var parsedComments = "Comments and ratings:\n"

        val commentList = cooments.split("|")
        for (comment in commentList) {
            if (comment != "") {
                parsedComments += "• $comment\n"
            }
        }

        return parsedComments
    }

    fun setData(newRecipe: Recipe) {
        this.currentRecipe = newRecipe;
        notifyDataSetChanged();
    }

    override fun getItemCount(): Int {
        return 1;
    }
}
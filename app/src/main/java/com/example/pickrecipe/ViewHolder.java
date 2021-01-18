package com.example.pickrecipe;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

public class ViewHolder extends RecyclerView.ViewHolder {

    View view;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);

        view = itemView;
    }

    public void setDetails(Context context, String title, String image){ //Data from firebase
        //Local data
        TextView imgTitle = view.findViewById(R.id.recipeTitle);
        ImageView img = view.findViewById(R.id.recipePic);

        imgTitle.setText(title);
        Picasso.get().load(image).into(img);//var image is from firebase - var img is local

        /*
        Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
        itemView.startAnimation(animation);
         */
    }
}

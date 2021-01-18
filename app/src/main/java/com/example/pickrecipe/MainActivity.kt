package com.example.pickrecipe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    var firebaseDatabase = FirebaseDatabase.getInstance();
    var reference = firebaseDatabase.getReference("Recipe");

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main);

        var mRecyclerView = RecyclerView(this);
    }

    override protected fun onStart() {
        super.onStart();
        //This is a test comment


        /*
        var firebaseRecyclerAdapter = FirebaseRecyclerAdapter<RecipeImage,ViewHolder>(
            RecipeImage::class.java,
            R.layout.recycler_recipe_list,
            ViewHolder::class.java,
            reference
        ){
            override protected fun populateViewHolder
        }
         */
    }

}
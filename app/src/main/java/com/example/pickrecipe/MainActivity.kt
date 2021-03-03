package com.example.pickrecipe

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pickrecipe.fragments.RecipeAdapter

class MainActivity : AppCompatActivity() {
    var linearLayout: RecyclerView.LayoutManager? = null
    var newAdapter: RecipeAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main);

        linearLayout = LinearLayoutManager(this);
        //newAdapter = RecipeAdapter()

        val recyclerViewDisplay : RecyclerView = findViewById(R.id.mainRecyclerRecipe);
    }
}
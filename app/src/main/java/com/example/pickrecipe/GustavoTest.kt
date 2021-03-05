package com.example.pickrecipe

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pickrecipe.fragment.RecipeAdapter
import com.example.pickrecipe.json.JsonTester

class GustavoTest : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gustavo_test);

        /*********************Tests*********************/
        //val recyclerViewDisplay : RecyclerView = findViewById(R.id.fragment);
        //val display = JsonTester(this,FILENAME,recyclerViewDisplay);
        /*********************Tests*********************/

        setupActionBarWithNavController(findNavController(R.id.fragment))
    }
/*
        //Navigate between Fragments
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fragment);
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

 */
}
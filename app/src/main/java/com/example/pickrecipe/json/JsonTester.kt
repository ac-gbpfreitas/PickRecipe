package com.example.pickrecipe.json

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pickrecipe.fragment.test.RecipeAdapterTest
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class JsonTester (context : Context, fileName : String, recyclerView : RecyclerView) {

    private var recipeJson = Types.newParameterizedType(List::class.java,RecipeJson::class.java)

    init{
        val jsonContent = FileReaderHelper.getDataFromAssets(context,fileName);
        val recipeMoshi : Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build();
        val recipeAdapter : JsonAdapter<List<RecipeJson>> = recipeMoshi.adapter(recipeJson);

        var recipeList : List<RecipeJson> = recipeAdapter.fromJson(jsonContent)!!;

        var layoutManager: RecyclerView.LayoutManager? = LinearLayoutManager(context)
        var recyclerAdapter: RecyclerView.Adapter<*>? = RecipeAdapterTest(recipeList,context);

        recyclerView.layoutManager = layoutManager;
        recyclerView.adapter = recyclerAdapter;
    }
}
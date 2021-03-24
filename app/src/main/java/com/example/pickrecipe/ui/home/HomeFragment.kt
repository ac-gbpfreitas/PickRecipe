package com.example.pickrecipe.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.pickrecipe.R

class HomeFragment : Fragment() {

    /*
    Classes Used in test:

        Raw data file: recipes.json. I will need to replace this with data coming from the backend

        Entities:
            Moshi: RecipeJson
            Room and Frontend: RecipeEntity (inside model folder inside db folder lol)

        JsonReader and Entity Converter: RecipeJsonReaderTester inside json folder
        Adapter: RecipeAdapter inside adapters folder
        Fragment: ListFragmentRecipe inside list folder inside fragment folder
        ViewModel: RecipeViewModel inside viewmodel folder
        Repository: RecipeRepository inside repository folder
        Dao: RecipeDao inside data folder inside db folder
        DB: RecipeIngredientDatabase inside those same folders


        Functionality:
            viewModel will access the database with the dao and insert all data using repository if not inserted
            them it will read all data that will come into a list of recipes, set that list to the adapter

        TODO:
            create the recipe database inside mongoDB and properly access it using socket, probably in the RecipeJsonReaderTester.
            modify both Moshi and room entities and uninstall the app to reload new DB schema
            see if I need to modify any data on the top-level view with the new attributes, see the adapter
            copy codes from viewModels and fragment. Good luck to me lol
            qd eu for converter o json eu dou parse nas strings com delimitador


    */

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}
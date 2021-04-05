package com.example.pickrecipe.ui.details

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pickrecipe.R
import com.example.pickrecipe.adapters.DetailRecipeAdapter
import com.example.pickrecipe.db.model.RecipeEntity
import com.example.pickrecipe.json.RecipeMoshi
import com.example.pickrecipe.json.UserJson
import com.example.pickrecipe.model.Recipe
import com.example.pickrecipe.viewmodel.RecipeViewModel
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import kotlinx.android.synthetic.main.fragment_details_recipe.view.*
import org.json.JSONObject
import java.io.InputStream
import java.net.URISyntaxException


class DetailsFragmentRecipe : Fragment(), DetailRecipeAdapter.ListItemListener {

    var mSocket: Socket? = null
    lateinit var id : String
    lateinit var title : String
    lateinit var rating : String
    lateinit var details : String
    lateinit var picture : String
    lateinit var directions : String
    lateinit var ingredients : String
    lateinit var comments : String
    lateinit var tags : String
    var checkPantryMatchString = ""
    var ingredientList = mutableListOf<String>()
    var pantry = mutableListOf<String>()
    var favorites = mutableListOf<String>()
    private var myRecipeJsonType = Types.newParameterizedType(RecipeMoshi::class.java)
    private val myUserJsonType = Types.newParameterizedType(UserJson::class.java)
    private lateinit var recipeDetailAdapter : DetailRecipeAdapter
    private lateinit var mRecipeViewModel : RecipeViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        mRecipeViewModel = ViewModelProvider(this).get(RecipeViewModel::class.java);

        id = arguments?.getString("id").toString();

        connectToBackend()

        mSocket?.on(Socket.EVENT_CONNECT, Emitter.Listener {
            Log.d("Connection to backend - Detail Fragment", "sending")
        });

        mSocket?.on("notification", onNotification)

        mSocket?.on("onAddComment", onAddComment)

        mSocket?.on("onGetRecipe",ongetRecipe)

        mSocket?.on("usernameSearchForLogin", onSearchUsername)

        mSocket?.on("onAddFavorite", onAddFavorite)

        mSocket?.on("onRemoveFavorite", onRemoveFavorite)

        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_details_recipe, container, false);

        id = arguments?.getString("id").toString();
        title = arguments?.getString("title").toString();
        rating = arguments?.getString("rating").toString();
        details = arguments?.getString("details").toString();
        picture = arguments?.getString("picture").toString();
        directions = arguments?.getString("directions").toString();
        ingredients = arguments?.getString("ingredients").toString();
        comments = arguments?.getString("comments").toString();
        tags = arguments?.getString("tags").toString();

        var recipeDetail = Recipe(
                id,title,details,directions,rating.toDouble(),picture,ingredients,comments,
                checkPantryMatchString,checkFavorite(),tags
        );

        Log.d("RECIPE","#"+id+" - "+title);

        recipeDetailAdapter = DetailRecipeAdapter(this@DetailsFragmentRecipe,false);
        recipeDetailAdapter.setData(recipeDetail);

        val recyclerView = view?.recyclerViewDetail;
        recyclerView?.adapter = recipeDetailAdapter;
        recyclerView?.layoutManager = LinearLayoutManager(requireContext());

        setHasOptionsMenu(true);
        return view;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val jsonUsername = "{'username':'${activity?.intent?.extras?.getString("username")}'}"
        mSocket?.emit("fetchUsernameForLogin",JSONObject(jsonUsername))

        super.onViewCreated(view, savedInstanceState)
    }

    fun connectToBackend() {
        var string: String? = ""
        try {
            val inputStream: InputStream = context?.assets!!.open("source.txt")
            val size: Int = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            string = String(buffer)
            Log.d("Read IP from txt", "Successfully read $string from txt")
        } catch (e: Exception) {
            Log.d("Read IP from txt", "Error: ${e.message.toString()}")
        }

        val ipAddress = string
        try {
            mSocket = IO.socket(ipAddress)

        } catch (e: URISyntaxException) {
            Log.d("URI error", e.message.toString())
        }

        try {
            mSocket?.connect()
            Log.d("Connection to Backend", "connected to $ipAddress, status: ${mSocket?.connected()}")
        } catch (e: Exception) {
            Log.d("Connection to Backend", "Failed to connect.")
        }
    }

    var onNotification = Emitter.Listener {
        val message = it[0] as String
        Log.d("Notification", message)
    }

    var onAddComment = Emitter.Listener {
        activity?.runOnUiThread{
            Toast.makeText(context, "Comment successfully added.", Toast.LENGTH_SHORT).show()
        }
    }

    var onSearchUsername = Emitter.Listener {

        val data = it[0] as String
        Log.d("Fetch Username", data)

        val moshi: Moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()
        val adapter : JsonAdapter<UserJson> = moshi.adapter(myUserJsonType)

        val user = adapter.fromJson(data)

        pantry = (user?.pantry as MutableList<String>?)!!
        favorites = (user?.favorites as MutableList<String>?)!!
        Log.d("Retrieve Pantry", "Pantry Retrieved.")
        val jsonRecipe = "{'id': '$id'}"
        mSocket?.emit("getRecipe",JSONObject(jsonRecipe))
    }

    var ongetRecipe = Emitter.Listener {

        val data = it[0] as String
        //Log.d("Fetch Username", data)

        val moshi: Moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()
        val adapter : JsonAdapter<RecipeMoshi> = moshi.adapter(myRecipeJsonType)

        val recipe = adapter.fromJson(data)

        if (recipe != null) {
            for (ingredient in recipe.ingredients) {
                ingredientList.add(ingredient.name)
            }
        }

        checkPantryMatchString = checkPantryResult(ingredientList,pantry)

        activity?.runOnUiThread {

            id = arguments?.getString("id").toString();
            title = arguments?.getString("title").toString();
            rating = arguments?.getString("rating").toString();
            details = arguments?.getString("details").toString();
            picture = arguments?.getString("picture").toString();
            directions = arguments?.getString("directions").toString();
            ingredients = arguments?.getString("ingredients").toString();
            comments = arguments?.getString("comments").toString();
            tags = arguments?.getString("tags").toString();

            var recipeDetail = Recipe(
                    id,title,details,directions,rating.toDouble(),picture,ingredients,comments,
                    checkPantryMatchString,checkFavorite(),tags
            );

            Log.d("RECIPE","#"+id+" - "+title);

            recipeDetailAdapter = DetailRecipeAdapter(this@DetailsFragmentRecipe,false);
            recipeDetailAdapter.setData(recipeDetail);

            val recyclerView = view?.recyclerViewDetail;
            recyclerView?.adapter = recipeDetailAdapter;
            recyclerView?.layoutManager = LinearLayoutManager(requireContext());

        }
    }



    var onAddFavorite = Emitter.Listener {
        activity?.runOnUiThread{
            Toast.makeText(context,"Recipe added to favorites.",Toast.LENGTH_SHORT).show()
        }
    }

    var onRemoveFavorite = Emitter.Listener {
        activity?.runOnUiThread{
            Toast.makeText(context,"Recipe removed from favorites.",Toast.LENGTH_SHORT).show()
        }
    }

    fun checkPantryResult(ingredients : List<String>, pantry : List<String>) : String {
        var result = ""
        val missingIngredients = mutableListOf<String>()
        for (ingredient in ingredients) {
            if (!pantry.contains(ingredient)) {
                missingIngredients.add(ingredient)
            }
        }

        if (missingIngredients.isEmpty()) result = "You're all set! You have every ingredient of this recipe in your pantry."
        else {
            result = "WARNING: some of this recipe's ingredients were not found in your pantry. You can either add them or find stores near you to buy them."
            result += "\nYou are missing:"
            for (missingIngredient in missingIngredients) {
                result += "\n${missingIngredient.trim().capitalize()}"
            }
        }

        return result
    }

    private fun checkFavorite() : Boolean {
        var isFavorite = false
        for (recipeId in favorites) {
            if (recipeId == id) isFavorite = true
        }

        return isFavorite
    }

    override fun submitComment(comment: String, userRating: Double) {


        val username = activity?.intent?.extras?.getString("username")
        val commentEntry = "$comment (by $username, Rating: ${userRating.toInt()})"
        Log.d("SUBMIT COMMENT",commentEntry)

        val jsonString = "{'comment':'${commentEntry}', 'id' : '${id}'}"
        mSocket?.emit("addComment", JSONObject(jsonString))

        val commentEntryCache = "$commentEntry|"
        comments += commentEntryCache


    }

    override fun updateRating(newRating: Double) {
        val jsonString = "{'rating':'${newRating}', 'id' : '${id}'}"
        mSocket?.emit("updateRating", JSONObject(jsonString))

        val recipeDetail = Recipe(
                id,title,details,directions,newRating,picture,ingredients,comments,
                checkPantryMatchString,checkFavorite(),tags
        )

        recipeDetailAdapter.setData(recipeDetail)

        //for the cache database to update
        val recipeEntityDetail = RecipeEntity(id,title,details,ingredients,directions,newRating,comments,picture,tags)
        mRecipeViewModel.addRecipe(recipeEntityDetail)
    }

    override fun addFavorite(id: String) {
        val jsonString = "{'username':'${activity?.intent?.extras?.getString("username")}','id':'$id'}"
        mSocket?.emit("addFavorite", JSONObject(jsonString))
    }

    override fun removeFavorite(id: String) {
        val jsonString = "{'username':'${activity?.intent?.extras?.getString("username")}','id':'$id'}"
        mSocket?.emit("removeFavorite", JSONObject(jsonString))
    }

}
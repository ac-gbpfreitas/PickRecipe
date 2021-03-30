package com.example.pickrecipe.fragment.display

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
import com.example.pickrecipe.model.Ingredient
import com.example.pickrecipe.model.Recipe
import com.example.pickrecipe.viewmodel.RecipeViewModel
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        connectToBackend()

        mSocket?.on(Socket.EVENT_CONNECT, Emitter.Listener {
            Log.d("Connection to backend - Detail Fragment", "sending")
        });

        mSocket?.on("notification", onNotification)

        mSocket?.on("onAddComment", onAddComment)

        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_details_recipe, container, false);

        var mRecipeViewModel : RecipeViewModel = ViewModelProvider(this).get(RecipeViewModel::class.java);

        id = arguments?.getString("id").toString();
        var title = arguments?.getString("title");
        var rating = arguments?.getString("rating");
        var details = arguments?.getString("details");
        var picture = arguments?.getString("picture");
        var directions = arguments?.getString("directions");
        var ingredients = arguments?.getString("ingredients");
        var comments = arguments?.getString("comments");


        var recipeDetail = Recipe(
                id!!,title!!,details!!,directions!!,rating!!.toDouble(),picture!!,ingredients!!,comments!!
        );


        var recipeDetailAdapter = DetailRecipeAdapter(this@DetailsFragmentRecipe);
        recipeDetailAdapter.setData(recipeDetail);

        val recyclerView = view.recyclerViewDetail;
        recyclerView.adapter = recipeDetailAdapter;
        recyclerView.layoutManager = LinearLayoutManager(requireContext());

        setHasOptionsMenu(true);
        return view;
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

    override fun submitComment(comment: String, rating: Double) {
        if (comment == "") Toast.makeText(context,"Please insert a comment.",Toast.LENGTH_LONG).show()
        else {
            val username = activity?.intent?.extras?.getString("username")
            val commentEntry = "$comment (by $username, Rating: ${rating.toInt()})"
            Log.d("SUBMIT COMMENT",commentEntry)

            val jsonString = "{'comment':'${commentEntry}', 'id' : '${id}'}"
            mSocket?.emit("addComment", JSONObject(jsonString))

            //TODO: UPDATE CACHE DATABASE AND COMMENT LIST
        }
    }

}
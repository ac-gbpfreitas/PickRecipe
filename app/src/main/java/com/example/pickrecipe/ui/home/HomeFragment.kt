package com.example.pickrecipe.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pickrecipe.R
import com.example.pickrecipe.adapters.RecipeAdapter
import com.example.pickrecipe.model.User
import com.example.pickrecipe.viewmodel.RecipeViewModel
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import kotlinx.android.synthetic.main.fragment_list_recipe.view.*
import org.json.JSONObject
import java.io.InputStream
import java.net.URISyntaxException

class HomeFragment : Fragment(){

    private lateinit var mRecipeViewModel : RecipeViewModel;
    var mSocket: Socket? = null
    private lateinit var data : String

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        connectToBackend()

        mSocket?.on(Socket.EVENT_CONNECT, Emitter.Listener {
            Log.d("Connection to backend - Home Fragment", "sending")
        });

        mSocket?.on("notification", onNotification)

        mSocket?.on("onGetRecipes", onGetRecipes)

        mSocket?.emit("getRecipes")

        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_list_recipe, container, false);


        var recipeAdapter = RecipeAdapter();
        val recyclerView = view.recyclerViewList;
        recyclerView.adapter = recipeAdapter;
        recyclerView.layoutManager = LinearLayoutManager(requireContext());

        mRecipeViewModel = ViewModelProvider(this).get(RecipeViewModel::class.java);

        mRecipeViewModel.readAllRecipes.observe(viewLifecycleOwner, {
            recipes -> recipeAdapter.setData(recipes);
        })

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

    var onGetRecipes = Emitter.Listener {
        data = it[0] as String
        mRecipeViewModel.deleteAllRecipes()
        mRecipeViewModel.addAllRecipes(data);
        Log.d("Got recipes", data)
    }
}
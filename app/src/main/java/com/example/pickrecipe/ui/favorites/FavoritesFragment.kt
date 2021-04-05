package com.example.pickrecipe.ui.favorites

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pickrecipe.R
import com.example.pickrecipe.adapters.RecipeAdapter
import com.example.pickrecipe.json.UserJson
import com.example.pickrecipe.ui.pantry.PantryAdapter
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import kotlinx.android.synthetic.main.fragment_favorites.*
import kotlinx.android.synthetic.main.fragment_pantry.*
import org.json.JSONObject
import java.io.InputStream
import java.net.URISyntaxException

class FavoritesFragment : Fragment() {

    private lateinit var favoritesViewModel: FavoritesViewModel
    var mSocket: Socket? = null
    private var favorites = mutableListOf<String>()
    private val myType = Types.newParameterizedType(UserJson::class.java)
    lateinit var username : String
    lateinit var listAdapter: RecipeAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        connectToBackend()

        mSocket?.on(Socket.EVENT_CONNECT, Emitter.Listener {
            Log.d("Connection to backend - Favorites Fragment", "sending")
        });

        mSocket?.on("notification", onNotification)

        mSocket?.on("usernameSearchForLogin", onSearchUsername)

        //I'll probably need to connect this in an event
//        favoritesViewModel =
//            ViewModelProvider(this).get(FavoritesViewModel::class.java)

        val bundle = activity?.intent?.extras
        if (bundle != null) {
            username = bundle.getString("username").toString()
            fetchUsername(username)
        }


        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val lm = LinearLayoutManager(context)
        recyclerViewFavoriteRecipes.layoutManager = lm
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

    var onSearchUsername = Emitter.Listener {
        val data = it[0] as String
        Log.d("Fetch Username", data)

        val moshi: Moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()
        val adapter : JsonAdapter<UserJson> = moshi.adapter(myType)

        val user = adapter.fromJson(data)
        favorites = (user?.favorites as MutableList<String>?)!!
        Log.d("Retrieve Favorites", "Favorites Retrieved.")

        favoritesViewModel = ViewModelProvider(this).get(FavoritesViewModel::class.java)
        val favoriteRecipes = favoritesViewModel.getFavoriteRecipes(favorites)

        activity?.runOnUiThread{
            listAdapter = RecipeAdapter()
            recyclerViewFavoriteRecipes.adapter = listAdapter
            listAdapter.setData(favoriteRecipes)

            if (favorites.isEmpty()) {
                Toast.makeText(context,"Empty list. Go to a recipe and star it to appear here!",Toast.LENGTH_LONG).show()
            }
        }

    }

    fun fetchUsername(username: String) {
        val jsonString = "{'username':${username}}"
        mSocket?.emit("fetchUsernameForLogin", JSONObject(jsonString))
    }
}
package com.example.pickrecipe.ui.pantry

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
import com.example.pickrecipe.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import kotlinx.android.synthetic.main.fragment_pantry.*
import org.json.JSONObject
import java.io.InputStream
import java.net.URISyntaxException

class PantryFragment : Fragment() {

    private lateinit var pantryViewModel: PantryViewModel
    var mSocket: Socket? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        pantryViewModel =
            ViewModelProvider(this).get(PantryViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_pantry, container, false)
        val textView: TextView = root.findViewById(R.id.text_slideshow)
        val fabAddIngredient : FloatingActionButton = root.findViewById(R.id.fabAddIngredient)
        pantryViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        connectToBackend()

        mSocket?.on(Socket.EVENT_CONNECT, Emitter.Listener {
            Log.d("Connection to backend - Pantry Fragment","sending")
        });

        mSocket?.on("notification",onNotification)

        mSocket?.on("onAddIngredient",onAddIngredient)

        fabAddIngredient.setOnClickListener {

            var username = ""
            val bundle = activity?.intent?.extras
            if (bundle != null) {
                username = bundle.getString("username","<username>")
            }

            val ingredientToAdd = spinnerIngredients.selectedItem.toString()

            addIngredient(username,ingredientToAdd)
        }

        return root
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
        Log.d("Notification",message)
    }

    var onAddIngredient = Emitter.Listener {
        val ingredient = it[0] as String
        activity?.runOnUiThread{
            Toast.makeText(context, "Ingredient $ingredient added to pantry.", Toast.LENGTH_SHORT).show()
        }
    }

    fun addIngredient(username : String, ingredient : String) {
        val jsonString = "{'username':'${username}', 'ingredient' : '${ingredient}'}"
        mSocket?.emit("addIngredientToPantry", JSONObject(jsonString))
    }
}
package com.example.pickrecipe

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.pickrecipe.json.*
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject
import java.io.InputStream
import java.net.URISyntaxException
import java.util.*

class LoginActivity : AppCompatActivity() {

    var mSocket: Socket? = null
    private val myType = Types.newParameterizedType(UserJson::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

                connectToBackend()

        mSocket?.on(Socket.EVENT_CONNECT, Emitter.Listener {
            Log.d("Connection to backend","sending")
        });

        mSocket?.on("notification",onNotification)

        mSocket?.on("usernameSearchForLogin",onSearchUsernameForLogin)

        val bundle = intent.extras
        if (bundle != null) {
            editTextLoginUsername.setText(bundle.getString("username"))
            editTextLoginPassword.setText(bundle.getString("password"))
        }

        btnOfflineMode.setOnClickListener {
            startActivity(Intent(this@LoginActivity,OfflineActivity::class.java))
        }

        buttonSignIn.setOnClickListener {

            val username = editTextLoginUsername.text.toString().trim()

            if (username == "") Toast.makeText(this, "Invalid Username or Password.", Toast.LENGTH_SHORT).show()
            //validate login and password. Fetch username data
            else fetchUsername(username)

        }

        textViewGoToRegistration.setOnClickListener{
            startActivity(Intent(this@LoginActivity, RegistrationActivity::class.java))
        }
    }

    fun connectToBackend() {
        var string: String? = ""
        try {
            val inputStream: InputStream = assets.open("source.txt")
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

    var onSearchUsernameForLogin = Emitter.Listener {
        val data = it[0] as String
        Log.d("Fetch Username",data)

        runOnUiThread{
            if (data == "null") {
                Toast.makeText(this, "Invalid Username or Password.", Toast.LENGTH_SHORT).show()
            } else {
                validateAndLogin(data)
            }
        }
    }

    fun fetchUsername(username : String) {
        val jsonString = "{'username':${username}}"
        mSocket?.emit("fetchUsernameForLogin", JSONObject(jsonString))
    }

    fun validateAndLogin(data : String) {
        val moshi: Moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        val adapter : JsonAdapter<UserJson> = moshi.adapter(myType)

        val user = adapter.fromJson(data)
        val password = editTextLoginPassword.text.toString()
        val passwordFromBackend = user?.password

        if (password == passwordFromBackend) {
            Toast.makeText(this, "Signing in as ${user.username}...", Toast.LENGTH_SHORT).show()

            val timerTask: TimerTask = object : TimerTask() {
                override fun run() {
                    finish()
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    //pass whatever data needs to be used in home activity (probably userID or username)

                    intent.putExtra("username",user.username)
                    startActivity(intent)
                }
            }
            val timer = Timer()
            timer.schedule(timerTask, 2500)
        } else {
            Toast.makeText(this, "Invalid Username or Password.", Toast.LENGTH_SHORT).show()
        }

    }

}
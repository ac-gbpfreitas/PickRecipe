package com.example.pickrecipe

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import kotlinx.android.synthetic.main.activity_registration.*
import org.json.JSONObject
import java.io.InputStream
import java.net.URISyntaxException
import java.util.*

class RegistrationActivity : AppCompatActivity() {

    var mSocket: Socket? = null
    var usernameExists : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        connectToBackend()

        mSocket?.on(Socket.EVENT_CONNECT, Emitter.Listener {
            Log.d("Connection to backend","sending")
        });

        mSocket?.on("notification",onNotification)

        mSocket?.on("usernameSearchResult",onUsernameSearchResult)

        buttonRegister.setOnClickListener {
            val username = editTextRegistrationUsername.text.toString().trim()
            fetchUsername(username)

        }

        textViewGoToLogin.setOnClickListener{
            startActivity(Intent(this@RegistrationActivity, LoginActivity::class.java))
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

    var onUsernameSearchResult = Emitter.Listener {
        val result = it[0] as Boolean
        usernameExists = result
        runOnUiThread {
            checkConstraintsAndAddUser()
        }
        Log.d("Fetch Username","Result from fetch username is: $result and usernameExists is $usernameExists")
    }

    fun fetchUsername(username : String) {
        val jsonString = "{'username':${username}}"
        mSocket?.emit("fetchUsername", JSONObject(jsonString))
    }

    fun checkConstraintsAndAddUser() {
        val username = editTextRegistrationUsername.text.toString().trim()
        val password = editTextRegistrationPassword.text.toString()
        val confirmPassword = editTextRegistrationConfirmPassword.text.toString()

        val usernameNotValid = username.contains(" ") || username.length < 3 || username == "null"
        val passwordNotValid = password.length < 3
        val passwordsMatch = password == confirmPassword && password.isNotEmpty() && !passwordNotValid

        if (usernameNotValid) {
            Toast.makeText(this, "Username must be 3 chars min, no spaces.", Toast.LENGTH_LONG).show()
        } else if (usernameExists) {
            Toast.makeText(this, "Username already exists, pick another.", Toast.LENGTH_LONG).show()
        } else if (passwordNotValid) {
            Toast.makeText(this, "Password must be 3 chars min.", Toast.LENGTH_LONG).show()
        } else if (passwordsMatch){
            //Add username data to mongo
            addUser(username,password)

            Toast.makeText(this, "Username $username successfully created.", Toast.LENGTH_SHORT).show()
            val timerTask: TimerTask = object : TimerTask() {
                override fun run() {
                    finish()
                    val intent = Intent(this@RegistrationActivity, LoginActivity::class.java)
                    intent.putExtra("username",username)
                    intent.putExtra("password",password)
                    startActivity(intent)
                }
            }
            val timer = Timer()
            timer.schedule(timerTask, 2500)
        } else {
            Toast.makeText(this, "Password not confirmed. Try Again.", Toast.LENGTH_LONG).show()
        }
    }

    fun addUser(username : String, password : String) {
        val jsonString = "{'username':${username}, 'password' : ${password}}"
        mSocket?.emit("register", JSONObject(jsonString))
    }

    override fun onDestroy() {
        super.onDestroy()
        mSocket?.disconnect()
    }




}
package com.example.pickrecipe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*
import java.util.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val bundle = intent.extras
        if (bundle != null) {
            editTextLoginUsername.setText(bundle.getString("username"))
            editTextLoginPassword.setText(bundle.getString("password"))
        }

        buttonSignIn.setOnClickListener {

            val username = editTextLoginUsername.text.toString().trim()
            val password = editTextLoginPassword.text.toString().trim()

            //validate login and password. Fetch username data

            Toast.makeText(this, "Signing in as $username...", Toast.LENGTH_SHORT).show()


            val timerTask: TimerTask = object : TimerTask() {
                override fun run() {
                    finish()
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    //pass whatever data needs to be used in home activity (probably userID or username)
                    intent.putExtra("username",username)
                    startActivity(intent)
                }
            }
            val timer = Timer()
            timer.schedule(timerTask, 2500)

        }

        textViewGoToRegistration.setOnClickListener{
            startActivity(Intent(this@LoginActivity, RegistrationActivity::class.java))
        }
    }
}
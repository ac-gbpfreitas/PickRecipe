package com.example.pickrecipe

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_registration.*
import java.util.*

class RegistrationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        buttonRegister.setOnClickListener {
            val username = editTextRegistrationUsername.text.toString().trim()
            val password = editTextRegistrationPassword.text.toString()
            val confirmPassword = editTextRegistrationConfirmPassword.text.toString()

            val usernameNotValid = username.contains(" ") || username.length < 3
            val passwordNotValid = password.length < 3
            val passwordsMatch = password.equals(confirmPassword) && password.isNotEmpty()
            //val usernameAlreadyExists

            if (usernameNotValid) {
                Toast.makeText(this, "Username must be 3 chars min, no spaces.", Toast.LENGTH_LONG).show()
            } else if (passwordNotValid) {
                Toast.makeText(this, "Password must be 3 chars min.", Toast.LENGTH_LONG).show()
            } else if (passwordsMatch){
                //Add username data to mongo
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

        textViewGoToLogin.setOnClickListener{
            startActivity(Intent(this@RegistrationActivity, LoginActivity::class.java))
        }

    }


}
package com.example.pickrecipe

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val timerTask: TimerTask = object : TimerTask() {
            override fun run() {
                finish()
                startActivity(Intent(this@SplashActivity, WelcomeActivity::class.java))
            }
        }
        val timer = Timer()
        timer.schedule(timerTask, 3000)

    }
}
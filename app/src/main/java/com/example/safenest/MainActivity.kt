package com.example.safenest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.coroutines.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        CoroutineScope(Dispatchers.Main).launch {
            delay(3000) // Delay for 3 seconds
            run()
        }
    }
    private fun run(){

        val intent = Intent(this, LoginSignup::class.java)
        startActivity(intent)
        finish()
    }
}
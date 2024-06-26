package com.example.safenest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext

class enter_name : AppCompatActivity() {
    private lateinit var displayName : EditText
    private lateinit var btnNext : Button
    private lateinit var dbHelper: DatabaseHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enter_name)
        displayName = findViewById<EditText>(R.id.getDisplayName)
        btnNext = findViewById(R.id.MoveToHome)
        dbHelper = DatabaseHelper(this)

        val userId = intent.getLongExtra("user_id", 0)

        btnNext.setOnClickListener{
            dbHelper.insertDisplayName(userId, displayName.text.toString())
            val intent = Intent(this, period::class.java)
            intent.putExtra("user_id", userId)
            startActivity(intent)
        }
    }
}

package com.example.safenest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class period : AppCompatActivity() {
    private lateinit var periodNext: Button
    private lateinit var lastDay: EditText
    private lateinit var estimatedDuedate: EditText
    private lateinit var dbHelper: DatabaseHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_period)
        dbHelper = DatabaseHelper(this)
        periodNext =findViewById(R.id.commitToHome)
        lastDay = findViewById<EditText>(R.id.lastDay)
        estimatedDuedate = findViewById<EditText>(R.id.estimatedDue)

        val userId = intent.getLongExtra("user_id", 0)
        Toast.makeText(this@period, userId.toString(), Toast.LENGTH_SHORT).show()

        periodNext.setOnClickListener(){
            dbHelper.insertPregnancyDetails(userId, lastDay.text.toString())
            val intent = Intent(this, home::class.java)
            intent.putExtra("user_id", userId)
            startActivity(intent)
            finish();
        }

    }
}
package com.example.safenest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class LoginPage : AppCompatActivity() {
    private lateinit var btnLogin: Button
    private lateinit var homeNameTextView: TextView
    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loginpage)
        btnLogin = findViewById(R.id.buttonLogin)
        usernameEditText = findViewById(R.id.login_username)
        passwordEditText = findViewById(R.id.login_password)

        dbHelper = DatabaseHelper(this)

        btnLogin.setOnClickListener {
            val username = usernameEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()
            val isValidUser = dbHelper.checkUser(username, password)

            if (isValidUser != null) {
                val loginCounter = dbHelper.getLoginCounter(isValidUser)
                if (loginCounter?.toInt() == 0) {
                    dbHelper.incrementLoginCounter(isValidUser)
                    navigateToFirstLoginLayouts(username, password, isValidUser)
                } else {
                    navigateToHome(username, password, isValidUser)
                }
            } else {
                Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun navigateToHome(username: String, password: String, user_id: Long) {
        val intent = Intent(this, home::class.java)
        intent.putExtra("username", username)
        intent.putExtra("password", password)
        intent.putExtra("user_id", user_id)
        startActivity(intent)
        finish()
    }

    private fun navigateToFirstLoginLayouts(username: String, password: String, user_id: Long) {
        val intent = Intent(this, SelectLanguage::class.java)
        intent.putExtra("username", username)
        intent.putExtra("password", password)
        intent.putExtra("user_id", user_id)
        startActivity(intent)
        finish()
    }
}


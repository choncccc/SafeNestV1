package com.example.safenest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class LoginSignup : AppCompatActivity() {
    private lateinit var btnLogin: Button
    private lateinit var btnSignUp: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_signup)
        btnLogin = findViewById(R.id.btnLogin)
        btnSignUp = findViewById(R.id.btnSignUp)

        btnLogin.setOnClickListener(){
            val intent = Intent(this, LoginPage::class.java)
            startActivity(intent)
        }
        btnSignUp.setOnClickListener(){
            val intent = Intent(this, SignUpPage::class.java)
            startActivity(intent)
        }
    }

}
package com.example.safenest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class SignUpPage : AppCompatActivity() {
    private lateinit var lname : EditText
    private lateinit var phone: EditText
    private lateinit var emailAdd : EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_page)

        val commitSignUp = findViewById<Button>(R.id.commitSignUp)

        commitSignUp.setOnClickListener{
            val userName = findViewById<EditText>(R.id.userName).text.toString()
            val fName = findViewById<EditText>(R.id.Fname).text.toString()
            val password = findViewById<EditText>(R.id.UserPassword).text.toString()
            val lname = findViewById<EditText>(R.id.Lname).text.toString()
            val phone = findViewById<EditText>(R.id.phoneNum).text.toString()
            val emailAdd = findViewById<EditText>(R.id.emailAdd).text.toString()

            // Check if required fields are empty
            if (userName.isBlank() || fName.isBlank() || password.isBlank() || lname.isBlank() || phone.isBlank() && emailAdd.isBlank()) {
                Toast.makeText(this@SignUpPage, "Please fill in all required fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val dbHelper = DatabaseHelper(this@SignUpPage)
            val rowId = dbHelper.insertSignUpUser(fName, lname, userName, password, phone, emailAdd)
            if (rowId != -1L) {
                Toast.makeText(this@SignUpPage, "User registered successfully!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, LoginPage::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this@SignUpPage, "Failed to register user!", Toast.LENGTH_SHORT).show()
            }
        }

    }
}

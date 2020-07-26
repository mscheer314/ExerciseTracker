package com.example.android.exercisetracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private val DEFAULT_USERNAME = "test@wgu.edu"
    private val DEFAULT_PASSWORD = "password"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val loginButton = findViewById<Button>(R.id.login)
        loginButton.setOnClickListener {
            if (credentialsAreCorrect()) {
                val intent = Intent(this, PrepWorkoutActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Incorrect credentials.\nPlease try again.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun credentialsAreCorrect(): Boolean {
        return (username.text.toString() == DEFAULT_USERNAME
                && password.text.toString() == DEFAULT_PASSWORD)
    }
}
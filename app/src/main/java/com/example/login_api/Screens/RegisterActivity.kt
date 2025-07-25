package com.example.login_api.Screens

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.login_api.API_Modul.User
import com.example.login_api.Helper.SharedPrefManager
import com.example.login_api.R
import com.example.login_api.Retrofit.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    private lateinit var emailInput: EditText
    private lateinit var nameInput: EditText
    private lateinit var registerBtn: Button
    private lateinit var loginLink: TextView
    private lateinit var sharedPrefManager: SharedPrefManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        sharedPrefManager = SharedPrefManager(this)

        emailInput = findViewById(R.id.emailInput)
        nameInput = findViewById(R.id.nameInput)
        registerBtn = findViewById(R.id.registerBtn)
        loginLink = findViewById(R.id.loginLink)

        registerBtn.setOnClickListener {
            val email = emailInput.text.toString().trim()
            val name = nameInput.text.toString().trim()


            when {
                name.isEmpty() -> {
                    nameInput.error = "Please enter your name"
                    nameInput.requestFocus()
                }
                email.isEmpty() -> {
                    emailInput.error = "Please enter your email"
                    emailInput.requestFocus()
                }
                !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                    emailInput.error = "Enter a valid email address"
                    emailInput.requestFocus()
                }
                else -> {
                    registerUser(name, email)
                }
            }
        }

        loginLink.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    private fun registerUser(name: String, email: String) {
        val newUser = User(name = name, email = email)

        ApiClient.instance.registerUser(newUser).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful && response.body() != null) {
                    Toast.makeText(this@RegisterActivity, "Registered!", Toast.LENGTH_SHORT).show()


                    sharedPrefManager.saveLogin(email)

                    startActivity(Intent(this@RegisterActivity, MainActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this@RegisterActivity, "Registration failed!", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Toast.makeText(this@RegisterActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}

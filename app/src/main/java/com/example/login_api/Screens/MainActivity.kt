package com.example.login_api.Screens

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.login_api.*
import com.example.login_api.API_Modul.Post
import com.example.login_api.Helper.SharedPrefManager
import com.example.login_api.Retrofit.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var logoutBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        logoutBtn = findViewById(R.id.logoutBtn)
        recyclerView = findViewById(R.id.recyclerPosts)

        recyclerView.layoutManager = LinearLayoutManager(this)

        logoutBtn.setOnClickListener {
            val sharedPrefManager = SharedPrefManager(this)
            sharedPrefManager.logout()

            Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        fetchPosts()
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                moveTaskToBack(true) // Close the app
            }
        })
    }


    private fun fetchPosts() {
        ApiClient.instance.getPosts().enqueue(object : Callback<List<Post>> {
            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                if (response.isSuccessful) {
                    val posts = response.body() ?: emptyList()
                    recyclerView.adapter = PostAdapter(posts)
                }
            }

            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

}

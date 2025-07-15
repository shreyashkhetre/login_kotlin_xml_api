package com.example.login_api.Retrofit

import com.example.login_api.API_Modul.Post
import com.example.login_api.API_Modul.User
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("users")
    fun getUsers(): Call<List<User>>

    @POST("users")
    fun registerUser(@Body user: User): Call<User>

    @GET("posts")
    fun getPosts(): Call<List<Post>>
}

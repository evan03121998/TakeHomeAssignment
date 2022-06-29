package com.example.takehomeassignment

import retrofit2.Call
import retrofit2.http.GET

interface API {
    @GET("users")
    fun getUsers(): Call<UserResponse>

}
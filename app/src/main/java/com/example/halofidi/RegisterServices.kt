package com.example.halofidi

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterServices {
    @POST("auth/local/register")
    fun saveData(@Body body:RegisterData) : Call<LoginResponse>
}
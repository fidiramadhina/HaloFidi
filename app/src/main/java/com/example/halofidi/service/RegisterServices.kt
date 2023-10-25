package com.example.halofidi.service

import com.example.halofidi.data.RegisterData
import com.example.halofidi.respon.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterServices {
    @POST("auth/local/register")
    fun saveData(@Body body: RegisterData) : Call<LoginResponse>
}
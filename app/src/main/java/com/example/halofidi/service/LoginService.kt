package com.example.halofidi.service


import com.example.halofidi.data.LoginData
import com.example.halofidi.respon.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {
    @POST("auth/local")
    fun getData(@Body body: LoginData): Call<LoginResponse>
}
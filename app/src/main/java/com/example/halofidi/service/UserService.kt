package com.example.halofidi.service

import com.example.halofidi.data.LoginData
import com.example.halofidi.respon.LoginResponse
import com.example.halofidi.respon.UserRespon
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UserService {
    @GET("users")
    fun getData() : Call<List<UserRespon>>

    @DELETE("users/{id}")
    fun delete(@Path("id") id : Int) : Call<UserRespon>
}
package com.example.halofidi

import com.google.gson.annotations.SerializedName

class LoginResponse {
    @SerializedName("jwt")
    var jwt: String = ""
}
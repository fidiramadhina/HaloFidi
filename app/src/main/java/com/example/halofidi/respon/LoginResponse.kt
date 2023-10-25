package com.example.halofidi.respon

import com.google.gson.annotations.SerializedName

class LoginResponse {
    @SerializedName("jwt")
    var jwt: String = ""
}
package com.example.halofidi.frontend

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.navigation.NavController
import com.example.halofidi.PreferencesManager
import com.example.halofidi.data.UpdateData
import com.example.halofidi.respon.LoginResponse
import com.example.halofidi.service.UserService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditUserPage(navController: NavController, userid : String?, usernameParameter: String?, context: Context = LocalContext.current){
    val preferencesManager = remember { PreferencesManager(context = context) }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf(TextFieldValue("")) }
    var email by remember { mutableStateOf(TextFieldValue("")) }
    if (usernameParameter != null) {
        username = usernameParameter
    }
    Scaffold (
        topBar = {
            TopAppBar(
                title = { Text(text = "Edit User") },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor =  MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
            )
        }, ) {
            innerPadding ->
        Column (modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
        ){
            if (userid != null) {
                Text(userid)
            }
            OutlinedTextField(value = username, onValueChange = { newText ->
                username = newText
            }, label = { Text("Username") })
            ElevatedButton(onClick = {
                var baseUrl = "http://10.0.2.2:1337/api/"
                val retrofit = Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(UserService::class.java)
                val call = retrofit.save(userid, UpdateData(username))
                call.enqueue(object : Callback<LoginResponse>) {
                    override fun onResponse(
                        call: Call<LoginResponse>,
                        response: Response<LoginResponse>
                    ) {
                        print(response.code())
                        if (response.code() == 200) {

                        } else if (response.code() == 400) {
                            print("error login")
                            var toast = Toast.makeText(
                                context,
                                "Username atau password salah",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        print(t.message)
                    }
                })
            }) {
                Text("Simpan")
            }
        }
    }
}
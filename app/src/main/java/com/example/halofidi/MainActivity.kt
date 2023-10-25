package com.example.halofidi

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.halofidi.data.LoginData
import com.example.halofidi.frontend.Homepage
import com.example.halofidi.respon.LoginResponse
import com.example.halofidi.service.LoginService
import com.example.halofidi.ui.theme.HaloFidiTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
//            HaloFidiTheme {
//                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        val sharedPreferences: SharedPreferences = LocalContext.current.getSharedPreferences("auth", Context.MODE_PRIVATE)
        val navController = rememberNavController()

        var startDestination: String
        var jwt = sharedPreferences.getString("jwt", "")
        if (jwt.equals("")) {
            startDestination = "greeting"
        }else {
            startDestination = "pagetwo"
        }

        NavHost(navController, startDestination = startDestination) {
               composable(route = "Greeting") {
                            Greeting(navController)
               }
               composable(route = "pagetwo"){
                   Homepage(navController)
               }
               composable(route = "createuserpage"){
                   CreateUserPage(navController)
               }
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Greeting(navController: NavController, context: Context = LocalContext.current) {

    var preferencesManager = remember { PreferencesManager(context = context) }
    var usernameState by remember { mutableStateOf(TextFieldValue("")) }
    var passwordState by remember { mutableStateOf(TextFieldValue("")) }

    val baseUrl = "http://10.217.17.11:1337/api/"
    var jwt by remember { mutableStateOf("") }

    jwt = preferencesManager.getData("jwt")
    
    Scaffold (
        topBar = {
            TopAppBar(
                title = { Text(text = "Halo") },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        },
        bottomBar = {
            BottomAppBar {
                Text(
                    text = "Terimakasih",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    ){
    innerPadding ->
    Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
             
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                value = usernameState,
                onValueChange = { usernameState = it },
                label = { Text(text = "Username") },
                placeholder = { Text(text = "Your Username") }
            )
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                visualTransformation = PasswordVisualTransformation(),
                value = passwordState,
                onValueChange = { passwordState = it},
                label = { Text(text = "Password") },
                placeholder = { Text(text = "Your Password") }
            )

            Spacer(modifier = Modifier.height(16.dp))
            //Spacer(modifier = Modifier.padding(5.dp))
//            ElevatedButton(onClick = { /* Handle login button click */ }) {
//                Text(text = "Login",
//                    style = TextStyle(fontWeight = FontWeight.Bold),
//                    color = Color.White
//                    )
//            }

            ElevatedButton(onClick = {
                //navController.navigate("pagetwo")
                val retrofit = Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(LoginService::class.java)
                val call = retrofit.getData(LoginData(usernameState.text, passwordState.text))
                call.enqueue(object: Callback<LoginResponse> {
                    override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>){
                        print(response.code())
                        if(response.code() == 200){
                            jwt = response.body()?.jwt!!
                            preferencesManager.saveData("jwt", jwt)
                            navController.navigate("pagetwo")
                        }else if(response.code() == 400){
                            print("error login")
                            var toast = Toast.makeText(context, "Usernam atau password salah", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<LoginResponse>, t: Throwable){
                        print(t.message)
                    }
                })
            }) {
                Text(text = "Submit")
            }
            Text(text = jwt)

        }
    }

}

//@Composable
//fun PageTwo() {
//    Text("Login successfully")
//}

//@Composable
//fun Greeting(name: String, modifier: Modifier = Modifier) {
//    Text(
//            text = "Fidi $name!",
//            modifier = modifier
//    )
//}

//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//   HaloFidiTheme {
//        Greeting("Android")
//    }
//}

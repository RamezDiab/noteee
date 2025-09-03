package com.ramez.notesapp.ui.Screens

import android.R.attr.text
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ramez.notesapp.Composables.MakeTextField
import com.ramez.notesapp.data.UserEntity
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(navController: NavController) {
    var name by rememberSaveable { mutableStateOf("")}
    var email by rememberSaveable { mutableStateOf("")}
    var password by rememberSaveable { mutableStateOf("")}

    val context = LocalContext.current
    val db = remember { AppDataBase.getDataBase(context) }
    val userDao = remember { db.userDao() }
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Spacer(modifier = Modifier.height(100.dp))
        Text(
            "Register",
            fontSize = 40.sp,
            color = Color.Blue,
            fontStyle = FontStyle.Italic,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(150.dp))

        MakeTextField("Full Name", onValueChange = {name =it}, text = name)
        Spacer(modifier = Modifier.height(30.dp))

        MakeTextField("Email ", onValueChange = {email =it}, text = email)
        Spacer(modifier = Modifier.height(30.dp))

        MakeTextField("Password 0", onValueChange = {password =it}, text = password)
        Spacer(modifier = Modifier.height(100.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                when {
                    name.isBlank() -> {
                        Toast.makeText(context, "Name is required", Toast.LENGTH_SHORT).show()
                    }
                    email.isBlank() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                        Toast.makeText(context, "Enter a valid email", Toast.LENGTH_SHORT).show()
                    }
                    password.length < 6 -> {
                        Toast.makeText(context, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        scope.launch {
                            val newUser = UserEntity(
                                name = name,
                                email = email,
                                password = password
                            )
                            userDao.addUser(newUser)


                            navController.navigate("Login")
                        }
                    }
                }
            }
        ) {
            Text("Register", fontSize = 20.sp)
            }
        }
}
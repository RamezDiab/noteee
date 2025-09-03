package com.ramez.notesapp.ui.Screens

import android.util.Log
import android.widget.Toast
import androidx.annotation.RestrictTo
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.ramez.notesapp.data.Prefs
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(navController: NavController) {
    val context = LocalContext.current

    val sharedPref = context.getSharedPreferences("notes_app_pref", 0)

    val savedEmail = sharedPref.getString("user_email", null)

    var email by rememberSaveable { mutableStateOf(savedEmail ?: "") }
    var password by rememberSaveable { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val db = remember { AppDataBase.getDataBase(context) }
    val userDao = remember { db.userDao() }


    // Auto-login check
    val rememberMe = sharedPref.getBoolean("remember_me", false)

    if (rememberMe && savedEmail != null) {

        // lw el user mf3l el remember me yd5lo 3la el home 3la tool
        LaunchedEffect(Unit) {
            navController.navigate("Home") {
                popUpTo("Login") { inclusive = true }
            }
        }
    }

    var checked by rememberSaveable { mutableStateOf(rememberMe) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(100.dp))
        Text(
            "Login",
            fontSize = 40.sp,
            color = Color.Blue,
            fontStyle = FontStyle.Italic,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(200.dp))
        MakeTextField("Email", onValueChange = { email = it }, text = email)
        Spacer(modifier = Modifier.height(20.dp))
        MakeTextField("Password", onValueChange = { password = it }, text = password)

//        var checked by remember { mutableStateOf(false)}

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(2.dp)
        ) {
            Checkbox(
                checked = checked,
                onCheckedChange = { isChecked ->
                    checked = isChecked
                    sharedPref.edit().putBoolean("remember_me", isChecked).apply()
                }
            )
            Text(
                text = "Remember me",
                fontSize = 20.sp,
                modifier = Modifier.padding(start = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(100.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                scope.launch {
                    val user = userDao.login(email, password)

                    Log.d("iddd",user?.id.toString())
                    if (user != null) {
                        Prefs.putString("id",user.id.toString())
                        navController.navigate("Home/${user.id}")
                    } else {

                        Toast.makeText(context, "Invalid email or password", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        ) {
            Text("Login", fontSize = 20.sp)
        }

        Text(
            "Don't Have Account?",
            modifier = Modifier.clickable(onClick = { navController.navigate("Register") })
        )
    }
}
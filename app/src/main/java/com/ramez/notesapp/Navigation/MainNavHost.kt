package com.ramez.notesapp.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ramez.notesapp.data.Prefs
import com.ramez.notesapp.ui.Screens.HomeScreen
import com.ramez.notesapp.ui.Screens.LoginScreen
import com.ramez.notesapp.ui.Screens.NoteScreen
import com.ramez.notesapp.ui.Screens.RegisterScreen

@Composable // All Notes for me 3shan arag3 b3deen
fun NavigationScreen (){
    val NavController= rememberNavController()
    val startDestination = if (Prefs.getString("id").isNotEmpty()) {
        "Home/${Prefs.getString("id")}"
    } else {
        "Login"
        }

    NavHost(navController = NavController, startDestination = startDestination){

        composable (route="Register"){
            RegisterScreen(NavController)
        }
        composable (route="Login"){
            LoginScreen(NavController)
        }

        // ana 3ndy note screen s7? lazm a5leeha ta5od el noteID as a parameter
        composable (route = "Home/{userId}",
            arguments = listOf(navArgument("userId") { type = NavType.IntType })){
            val userId = it.arguments?.getInt("userId") ?: 0
            HomeScreen(NavController,userId)
        }
        composable (route="Note/{UserId}", arguments = listOf(navArgument("UserId") { type = NavType.IntType })){
            val userId = it.arguments?.getInt("UserId") ?: 0
            NoteScreen (NavController, userid = userId)
            }
}
}
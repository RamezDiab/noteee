package com.ramez.notesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.ramez.notesapp.Navigation.NavigationScreen
import com.ramez.notesapp.data.Prefs

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //  enableEdgeToEdge()
        setContent {
            Prefs.init(context = this)
            val context = LocalContext.current
            val db = remember { AppDataBase.getDataBase(context) }
            val userDao = remember { db.userDao() }
            val noteDao = remember { db.noteDao() }
            NavigationScreen()
        }
            }
        }

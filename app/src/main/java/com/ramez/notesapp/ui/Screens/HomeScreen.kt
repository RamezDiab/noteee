package com.ramez.notesapp.ui.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background

// import androidx.compose.foundation.R
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramez.notesapp.R;


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Homescreen() {
    Scaffold(

        topBar = {
            TopAppBar(
                title = { Text("Notes", fontWeight = FontWeight.Bold, fontSize = 40.sp) },
                actions = { // أيقونة البحث
                    IconButton(
                        onClick = { },
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .background(
                                color = Color(0xFF2C2C2C), // خلفية غامقة
                                shape = CircleShape
                            )
                    )
                    {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            tint = Color.White
                        )
                    }
                }
            )
        },

        floatingActionButton = {
            FloatingActionButton(onClick = { }
            , containerColor = Color(0xFF2C2C2C))
            {
                Icon(
                    Icons.Default.Add, contentDescription = "",
                    tint = Color.White
                )
            }
        }
    )
    { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(100.dp))
            Image(
                painter = painterResource(id = R.drawable.emptyhome),
                ""
            )


            Text(
                "Create your First note!!", fontWeight = FontWeight.Bold, fontSize = 23.sp
            )
        }
    }
}

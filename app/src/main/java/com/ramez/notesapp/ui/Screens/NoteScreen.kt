package com.ramez.notesapp.ui.Screens

import androidx.compose.material.icons.filled.Add

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteScreen(
    onBack: () -> Unit = {}
) {
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = { onBack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                actions = {
                    // زرار الصورة
                    IconButton(
                        onClick = { },
                        modifier = Modifier
                            .background(Color(0xFF2C2C2C), RoundedCornerShape(8.dp))
                    )
                    {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add Image",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                   // containerColor = Color(0xFF1C1C1C)
                )
            )
        },
        // containerColor = Color(0xFF1C1C1C)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            // العنوان
            TextField(
                value = title,
                onValueChange = { title = it },
                placeholder = { Text("Title",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.SemiBold) },
                textStyle = TextStyle(fontSize = 28.sp,
                    fontWeight = FontWeight.SemiBold, color = Color.Black),

//                colors = TextFieldDefaults.TextFieldDefaults.colors(
//                    containerColor = Color.Transparent,
//                    cursorColor = Color.White,
//                    focusedIndicatorColor = Color.Transparent,
//                    unfocusedIndicatorColor = Color.Transparent
//                ),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            // المحتوى
            TextField(
                value = content,
                onValueChange = { content = it },
                placeholder = { Text("Type something...",
                    color = Color.Gray) },
                textStyle = TextStyle(fontSize = 18.sp, color = Color.Black),

//                colors = TextFieldDefaults.TextFieldDefaults.colors(
//                    containerColor = Color.Transparent,          // الخلفية شفافة (هتظهر زي الخلفية اللي وراه)
//                    focusedIndicatorColor = Color.Transparent,   // خط البوردر (فوكَس) شيلته
//                    unfocusedIndicatorColor = Color.Transparent, // خط البوردر (عادي) شيلته
//                    disabledIndicatorColor = Color.Transparent
//                ),
                modifier = Modifier
                    .fillMaxSize()
            )
        }
    }
}
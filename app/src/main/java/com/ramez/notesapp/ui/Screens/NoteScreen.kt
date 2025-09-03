package com.ramez.notesapp.ui.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ramez.notesapp.data.NoteEntity
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable              //All comments for me 3shan arag3
fun NoteScreen(
    navController: NavController,
    noteId: Int? = null, // hena hnst2bl el note id
    userid:Int
) {
    val context = LocalContext.current
    val db = remember { AppDataBase.getDataBase(context) }
    val noteDao = remember { db.noteDao() }
    val scope = rememberCoroutineScope()

    var title by rememberSaveable { mutableStateOf("") }
    var content by rememberSaveable { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }

    // 3shan ageeb note 2deema mn el database
    LaunchedEffect(noteId) {
        if (noteId != null) {
            val oldNote = noteDao.getNoteById(noteId)
            title = oldNote.title.toString()
            content = oldNote.body.toString()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = { navController.navigate("Home") }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.Black
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = { showDialog = true },
                        modifier = Modifier
                            .background(
                                Color(0xFF2C2C2C),
                                RoundedCornerShape(8.dp)
                            )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Done,
                            contentDescription = "Done",
                            tint = Color.White
                        )
                    }
                },
            )
        }
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
                placeholder = {
                    Text(
                        "Title",
                        fontSize = 30.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                textStyle = TextStyle(
                    fontSize = 28.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                ),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent
                ),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            // el content
            TextField(
                value = content,
                onValueChange = { content = it },
                placeholder = {
                    Text(
                        "Type something...",
                        color = Color.Gray
                    )
                },
                textStyle = TextStyle(fontSize = 18.sp, color = Color.Black),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent
                ),
                modifier = Modifier.fillMaxSize()
            )
        }


        // Da el AlerDialog (elly by2oly confirm w discard)
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("Save Note") },
                text = { Text("Do you want to confirm or discard this note?") },
                confirmButton = {
                    TextButton(onClick = {
                        scope.launch {
                            if (noteId != null) {
                                // Update
                                val updatedNote = NoteEntity(
                                    id = noteId,
                                    title = title,
                                    body = content,
                                    userId = userid
                                )
                                noteDao.updateNote(updatedNote)
                            } else {
                                // Add
                                val newNote = NoteEntity(
                                    title = title,
                                    body = content,
                                    userId = userid
                                )
                                noteDao.addNote(newNote)
                            }
                        }
                        navController.navigate("Home/${userid}") {
                            popUpTo("Home/${userid}") { inclusive = true }
                        }
                        showDialog = false
                    }) {
                        Text("Confirm")
                    }
                },
                dismissButton = {
                    TextButton(onClick = {
                        showDialog = false
                    }) {
                        Text("Discard")
                    }
                }
            )
        }
    }
}
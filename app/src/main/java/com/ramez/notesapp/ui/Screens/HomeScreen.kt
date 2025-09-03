package com.ramez.notesapp.ui.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ramez.notesapp.Composables.NoteCard
import com.ramez.notesapp.Composables.SwipeToDeleteContainer
import com.ramez.notesapp.R
import com.ramez.notesapp.data.NoteEntity
import com.ramez.notesapp.data.UserEntity
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, userId : Int) {
    val context = LocalContext.current
    val db = remember { AppDataBase.getDataBase(context) }
    val noteDao = remember { db.noteDao() }
    var userEntity by remember { mutableStateOf<UserEntity?>(null)}
    val notes by noteDao.readAllData(userId).collectAsState(initial = emptyList())

    val coroutineScope = rememberCoroutineScope()

    var searchQuery by remember { mutableStateOf("") }
    var isSearching by remember { mutableStateOf(false) }
    val userDao = remember { db.userDao() }

    val sharedPref = context.getSharedPreferences("notes_app_pref", 0)

    LaunchedEffect(Unit) {
        userEntity = userDao.getUserById(userId)
        }

    val filteredNotes = if (searchQuery.isNotEmpty()) {
        notes.filter { note -> note.title?.contains(searchQuery, ignoreCase = true) == true }
    } else {
        notes
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    if (isSearching) {
                        TextField(
                            value = searchQuery,
                            onValueChange = { searchQuery = it },
                            placeholder = { Text("Search notes...") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                    } else {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Welcome ${userEntity?.name?: "Guest"}, ",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = "Notes App",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.SemiBold
                                )
                        }
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            isSearching = !isSearching
                            if (!isSearching) searchQuery = ""
                        },
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .background(
                                color = Color(0xFF2C2C2C),
                                shape = CircleShape
                            )
                    ) {
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
            FloatingActionButton(
                onClick = { navController.navigate("Note/${userId}")},
                containerColor = Color(0xFF2C2C2C)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add",
                    tint = Color.White
                )
            }
        },
        bottomBar = {
            Text(
                text = "Sign Out!",
                modifier = Modifier
                    .clickable {
                        // ms7 data el remember me
                        sharedPref.edit().putBoolean("remember_me", false).remove("user_email")
                            .apply()


                        // arg3 ll login screen w kman lw dost back myrg3sh ll home
                        navController.navigate("Login") {
                            popUpTo("Home") { inclusive = true }
                        }
                    }
                    .padding(16.dp),
                color = Color.Red,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        }
    ) { innerPadding ->
        LazyColumn(
            contentPadding = innerPadding,
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 40.dp)
        ) {
            if (filteredNotes.isEmpty()) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                            .padding(top = 150.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.emptyhome),
                            contentDescription = "Empty Notes",
                            modifier = Modifier.size(200.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Create your First note!!",
                            fontWeight = FontWeight.Bold,
                            fontSize = 23.sp
                        )
                    }
                }
            } else {
                items(filteredNotes,
                    key = { note -> note.id ?: 0 }
                ) { note ->
                    SwipeToDeleteContainer(
                        onDelete = {
                            coroutineScope.launch {
                                noteDao.deleteNote(note)
                            }
                        }
                    ) {
                        NoteCard(
                            note = note,
                            onClick = { navController.navigate("Note/${note.id}") },
                            onDelete = {
                                coroutineScope.launch {
                                    noteDao.deleteNote(note)
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}




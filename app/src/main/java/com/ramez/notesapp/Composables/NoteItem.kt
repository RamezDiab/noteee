package com.ramez.notesapp.Composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.room.util.TableInfo

@Composable
fun NoteItem(Title: String){
    Card (modifier = Modifier
        .fillMaxWidth()
        .background(color = Color.Blue)
        .clickable(onClick = {})
    )
    {
        Text(Title, fontSize = 23.sp, fontWeight = FontWeight.SemiBold)
        }
}
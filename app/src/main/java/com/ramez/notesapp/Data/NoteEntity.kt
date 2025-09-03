package com.ramez.notesapp.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "note_table")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val title: String?,
    val body: String?,
    val timestamp: Long = System.currentTimeMillis(),
    val userId: Int


)

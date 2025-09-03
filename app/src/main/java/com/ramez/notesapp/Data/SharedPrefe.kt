package com.ramez.notesapp.data

import android.content.Context
import android.content.SharedPreferences

object Prefs {
    private const val PREFS_NAME = "MyPrefs"
    private lateinit var sharedPreferences: SharedPreferences

    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun putString(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    fun getString(key: String, default: String = ""): String {
        return sharedPreferences.getString(key, default) ?:default
        }
}
package com.example.expensetracker.helper

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

object NightModManager {
    private lateinit var sharedPreferences: SharedPreferences

    private const val NIGHT_MODE_KEY = "night_mode"



    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences("night_mode_prefs", Context.MODE_PRIVATE)
    }

    fun isNightModeEnabled(): Boolean {
        return sharedPreferences.getBoolean(NIGHT_MODE_KEY, false)
    }

    fun setNightModeEnabled(enabled: Boolean) {
        sharedPreferences.edit { putBoolean(NIGHT_MODE_KEY, enabled) }
    }
}
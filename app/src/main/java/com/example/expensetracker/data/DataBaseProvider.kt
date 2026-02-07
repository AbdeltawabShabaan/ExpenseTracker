package com.example.expensetracker.data

import android.content.Context
import androidx.room.Room

object DataBaseProvider {
    private var INSTANCE: AppDataBase? = null
    fun getDatabase(context: Context): AppDataBase {
        if (INSTANCE == null) {
            synchronized(this) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java,
                    "expense_database"
                ).fallbackToDestructiveMigration().build()
            }
        }
        return INSTANCE!!
    }
}
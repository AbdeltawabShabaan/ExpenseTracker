package com.example.expensetracker.data

import androidx.room.Database
import androidx.room.RoomDatabase
@Database(
    entities = [ExpenseData::class],
    version = 4,
    exportSchema = false)
abstract class AppDataBase(): RoomDatabase() {
    abstract fun expenseDao(): DAO
}
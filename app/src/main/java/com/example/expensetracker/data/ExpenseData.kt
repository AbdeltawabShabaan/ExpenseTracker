package com.example.expensetracker.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ExpenseData(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "amount")
    val amount: Double?,
    @ColumnInfo(name = "category")
    val category: String,
    @ColumnInfo(name = "date")
    val date: Long= System.currentTimeMillis(),
    @ColumnInfo(name = "description")
    val description: String?=null,
    @ColumnInfo(name = "isUpdate")
    val isUpdate: Boolean=false
)

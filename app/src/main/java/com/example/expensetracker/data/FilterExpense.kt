package com.example.expensetracker.data

data class FilterExpense(
    val category: String? = null,
    val fromDate: Long? = null,
    val toDate: Long? = null
)

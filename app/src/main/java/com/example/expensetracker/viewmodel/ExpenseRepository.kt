package com.example.expensetracker.viewmodel

import androidx.lifecycle.LiveData
import com.example.expensetracker.data.DAO
import com.example.expensetracker.data.ExpenseData

class ExpenseRepository(private val expenseDao: DAO) {
    fun getExpenses(category: String?=null,start: Long?=null,end: Long?=null): LiveData<List<ExpenseData>> =
        expenseDao.getAllExpenses(category,start,end)
    fun getExpenseById(id: Int): LiveData<ExpenseData> = expenseDao.getExpenseById(id)
    suspend fun insertExpenses(expenseData: ExpenseData)=expenseDao.insertExpense(expenseData)
    suspend fun deleteExpenses(expenseData: ExpenseData)=expenseDao.deleteExpense(expenseData)
    suspend fun updateExpenseById(id: Int, category: String, amount: Double?) =expenseDao.updateExpenseById(id, category, amount)
}





package com.example.expensetracker.viewmodel

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.expensetracker.data.DAO
import com.example.expensetracker.data.ExpenseData
import kotlinx.coroutines.flow.Flow

class ExpenseRepository(private val expenseDao: DAO) {
    fun getExpenses(category: String?=null,start: Long?=null,end: Long?=null): LiveData<List<ExpenseData>> =
        expenseDao.getAllExpenses(category,start,end)

    fun expenseWithPagination(): Flow<PagingData<ExpenseData>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                prefetchDistance = 2,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { expenseDao.expenseWithPagination() }
        ).flow
    }


    fun getExpenseById(id: Int): LiveData<ExpenseData> = expenseDao.getExpenseById(id)

    suspend fun insertExpenses(expenseData: ExpenseData)=expenseDao.insertExpense(expenseData)
    suspend fun updateExpenses(expenseData: ExpenseData)=expenseDao.updateExpense(expenseData)
    suspend fun deleteExpenses(expenseData: ExpenseData)=expenseDao.deleteExpense(expenseData)

    fun getTotalAmount(): LiveData<Double> = expenseDao.getTotalAmount()

    suspend fun updateExpenseById(id: Int, category: String, amount: Double?) =expenseDao.updateExpenseById(id, category, amount)
}





package com.example.expensetracker.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.expensetracker.data.DataBaseProvider
import com.example.expensetracker.data.ExpenseData
import kotlinx.coroutines.launch

class ExpenseViewModel(app: Application): ViewModel() {


    private val dao= DataBaseProvider.getDatabase(app).expenseDao()
    private val repository = ExpenseRepository(dao)



    fun expenses(category: String?=null,start: Long?=null,end: Long?=null): LiveData<List<ExpenseData>> {
        return repository.getExpenses(category,start,end)
    }
    val expenseWithPagination=repository.expenseWithPagination().cachedIn(viewModelScope)

    fun getTotalAmount(): LiveData<Double> = repository.getTotalAmount()
    fun filterByCategory(category: String): LiveData<List<ExpenseData>> = repository.getExpenses(category = category)
    fun filterByDate(start: Long, end: Long): LiveData<List<ExpenseData>> = repository.getExpenses(start = start, end = end)
     fun expenseById(id: Int): LiveData<ExpenseData> = repository.getExpenseById(id)



    // Methods to interact with the repository
    suspend fun insertExpense(expenseData: ExpenseData){
        repository.insertExpenses(expenseData)
    }

    // Methods to interact with the repository
    suspend fun updateExpense(expenseData: ExpenseData){
        repository.updateExpenses(expenseData)
    }

    // Methods to interact with the repository
    fun deleteExpense(expenseData: ExpenseData) {
        viewModelScope.launch { repository.deleteExpenses(expenseData) }
    }

    // Methods to updateById the expenses with the reposito
    suspend fun updateById(id: Int, category: String, amount: Double?){
        repository.updateExpenseById(id, category, amount)
    }




}
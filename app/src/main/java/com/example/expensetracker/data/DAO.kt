package com.example.expensetracker.data

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
@Dao
interface DAO {
    @Query("SELECT * FROM ExpenseData WHERE (:category IS NULL OR category=:category) AND (:start IS NULL OR date=:start) AND (:end IS NULL OR date=:end) ORDER BY date DESC")
    fun getAllExpenses(category: String?=null,start: Long?=null,end: Long?=null): LiveData<List<ExpenseData>>

    @Query("SELECT * FROM ExpenseData ORDER BY date DESC")
    fun expenseWithPagination():PagingSource<Int, ExpenseData>

    @Query("SELECT * FROM ExpenseData WHERE id = :id")
    fun getExpenseById(id: Int): LiveData<ExpenseData>

    @Query("""  SELECT * FROM ExpenseData WHERE date BETWEEN :start AND :end""")
    fun filterByDate(start: Long, end: Long): LiveData<List<ExpenseData>>

    @Insert
    suspend fun insertExpense(expenseData: ExpenseData)
    @Update
    suspend fun updateExpense(expenseData: ExpenseData)
    @Delete
    suspend fun deleteExpense(expenseData: ExpenseData)

    @Query("SELECT SUM(amount) FROM ExpenseData")
    fun getTotalAmount(): LiveData<Double>

    @Query( """  UPDATE ExpenseData SET category = :category, amount = :amount, date = :date, isUpdate = :isUpdate WHERE id = :id""")
    suspend fun updateExpenseById(
        id: Int,
        category: String,
        amount: Double?,
        date: Long=System.currentTimeMillis(),
        isUpdate: Boolean = true
    )


}
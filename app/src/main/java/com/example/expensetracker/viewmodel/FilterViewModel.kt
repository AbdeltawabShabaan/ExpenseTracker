package com.example.expensetracker.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.expensetracker.data.FilterExpense

class FilterViewModel: ViewModel() {
    private val _filterData = MutableLiveData<FilterExpense?>()
    val filterData: LiveData<FilterExpense?> = _filterData
    fun sendList(list:FilterExpense){
        _filterData.value = list
    }
}
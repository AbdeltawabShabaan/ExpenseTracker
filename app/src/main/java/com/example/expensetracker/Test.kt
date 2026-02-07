package com.example.expensetracker

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.expensetracker.data.ExpenseData
import com.example.expensetracker.data.FilterExpense
import com.example.expensetracker.helper.Constance
import com.example.expensetracker.viewmodel.ExpenseViewModel
import java.util.Calendar


fun main(){
    val long: Long= Constance.convertStringDateToLong("1/1/2026 20:00")
    print(long)
}
//method get cat , from , to from dialogfragment and send it to viewmodel,her location in viewmodel is expenseviewmodel
//private val _filterData = MutableLiveData<FilterExpense>()
//val filterData: LiveData<FilterExpense>
//    get() = _filterData as LiveData<FilterExpense>
//
//fun sendList(list:FilterExpense){
//    _filterData.value = list
//}
//
//// LiveData that holds the list of expenses ,her location in viewmodel is expenseviewmodel
//fun expenses(): LiveData<List<ExpenseData>> {
//    val cat=filterData.value?.category
//    val from=filterData.value?.fromDate
//    val to=filterData.value?.toDate
//    return if (cat == null && from == null && to == null)
//        repository.getExpenses()
//    else
//        repository.getExpenses(cat,from, to)
//}
//
//// filter method this mathod in dialogfragment
//
//
//applyBTN.setOnClickListener {
//    val category = categoryTV.text.toString()
//    val date = dateTV.text.toString()
//    val dateRange = getDateRange(date)
//    val list = FilterExpense(category, dateRange?.first, dateRange?.second)
//    ExpenseViewModel(requireActivity().application).sendList(list)
//    dismiss()
//}
//
//fun getDateRange(filter: String): Pair<Long, Long>? {
//    val calendar = Calendar.getInstance()
//
//    return when (filter) {
//        "This Day" -> {
//            calendar.set(Calendar.HOUR_OF_DAY, 0)
//            calendar.set(Calendar.MINUTE, 0)
//            calendar.set(Calendar.SECOND, 0)
//            calendar.set(Calendar.MILLISECOND, 0)
//            val start = calendar.timeInMillis
//
//            calendar.add(Calendar.DAY_OF_MONTH, 1)
//            val end = calendar.timeInMillis
//            start to end
//        }
//
//        "This month" -> {
//            calendar.set(Calendar.DAY_OF_MONTH, 1)
//            calendar.set(Calendar.HOUR_OF_DAY, 0)
//            calendar.set(Calendar.MINUTE, 0)
//            calendar.set(Calendar.SECOND, 0)
//            calendar.set(Calendar.MILLISECOND, 0)
//            val start = calendar.timeInMillis
//
//            calendar.add(Calendar.MONTH, 1)
//            val end = calendar.timeInMillis
//            start to end
//        }
//
//        "This year" -> {
//            calendar.set(Calendar.MONTH, Calendar.JANUARY)
//            calendar.set(Calendar.DAY_OF_MONTH, 1)
//            calendar.set(Calendar.HOUR_OF_DAY, 0)
//            calendar.set(Calendar.MINUTE, 0)
//            calendar.set(Calendar.SECOND, 0)
//            calendar.set(Calendar.MILLISECOND, 0)
//            val start = calendar.timeInMillis
//
//            calendar.add(Calendar.YEAR, 1)
//            val end = calendar.timeInMillis
//            start to end
//        }
//
//        else -> null // Any time
//    }}
//
//when use it in main activity the filter did not work
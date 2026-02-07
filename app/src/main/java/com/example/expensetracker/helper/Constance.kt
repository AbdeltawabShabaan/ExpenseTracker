package com.example.expensetracker.helper

import android.app.DatePickerDialog
import android.content.Context
import android.content.SharedPreferences
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

object Constance {
    const val ARG_EXPENSE_ID = "ARG_EXPENSE_ID"

    fun dateFormated(date: Long): String{
        val dateFormat= SimpleDateFormat("dd/MM/yyyy HH:mm",Locale.getDefault())
        val formattedDate=dateFormat.format(Date(date))
        return formattedDate
    }

    fun convertStringDateToLong(date: String):Long{
        val dateFormat= SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        val formattedDate =dateFormat.parse(date)!!.time
        return formattedDate
    }

    fun calenderPicker(context: Context,onDateSelected:(String, Long)->Unit){
        val calendar= Calendar.getInstance()
        val year=calendar.get(Calendar.YEAR)
        val month=calendar.get(Calendar.MONTH)
        val day=calendar.get(Calendar.DAY_OF_MONTH)
        val datePickerDialog= DatePickerDialog(context,{_,selectedYear,selectedMonth,selectedDay->
            calendar.set(selectedYear,selectedMonth,selectedDay)
            val dateFormat= SimpleDateFormat("dd/MM/yyyy HH:mm",Locale.getDefault())
            val formattedDate=dateFormat.format(calendar.time)
            onDateSelected(formattedDate,calendar.timeInMillis)
                                                       }
            ,year,month,day
        )
        datePickerDialog.show()
    }

    private lateinit var dateSharedPref: SharedPreferences
    private const val DATE_PREF_KEY = "date_pref_key"

    fun init(context: Context) {
        dateSharedPref = context.getSharedPreferences("date_pref", Context.MODE_PRIVATE)
    }
    fun saveDate(date: String) {
        dateSharedPref.edit().putString(DATE_PREF_KEY, date).apply()
    }
    fun getDate(): String? {
        return dateSharedPref.getString(DATE_PREF_KEY, null)
    }

}
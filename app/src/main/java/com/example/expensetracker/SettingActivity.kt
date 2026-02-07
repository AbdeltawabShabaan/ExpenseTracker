package com.example.expensetracker

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.expensetracker.adaper.ExpenseAdapter
import com.example.expensetracker.databinding.ActivitySettingBinding
import com.example.expensetracker.helper.Constance
import com.example.expensetracker.viewmodel.ExpenseViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.util.Calendar

class SettingActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingBinding
    private lateinit var expenseAdapter: ExpenseAdapter
    private lateinit var viewModel: ExpenseViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySettingBinding.inflate(layoutInflater)
        Constance.init(this)
        viewModel = ExpenseViewModel(application)
        val filter=Constance.getDate()
        val search=binding.searchET.text.toString()

        setContentView(binding.root)
        binding.menuTV.setOnClickListener {
            val dialog=Filter()
            dialog.show(supportFragmentManager,"filter")
        }
        setupRecyclerView()

        binding.outlinedTextField.setEndIconOnClickListener {
            observeExpense()
        }


    }
    fun setupRecyclerView() {
        expenseAdapter = ExpenseAdapter(emptyList()) { expenseId ->
            val dialog = ExpenseDialog.newInstanceForEdit(expenseId)
            dialog.show(supportFragmentManager, "expenseDialog")
        }
        binding.expRV.adapter = expenseAdapter
    }
    fun observeExpense() {
        val filter=Constance.getDate()
        val search=binding.searchET.text.toString()
        val start=getDateRange(filter)?.first
        val end=getDateRange(filter)?.second
        viewModel.expenses(category = search, start = start, end = end).observe(this) { expenseList ->
            expenseAdapter.notifyItemChange(expenseList)
        }
    }

    fun getDateRange(filter: String?): Pair<Long, Long>? {
        val calendar = Calendar.getInstance()

        return when (filter) {
            "This Day" -> {
                calendar.set(Calendar.HOUR_OF_DAY, 0)
                calendar.set(Calendar.MINUTE, 0)
                calendar.set(Calendar.SECOND, 0)
                calendar.set(Calendar.MILLISECOND, 0)
                val start = calendar.timeInMillis

                calendar.add(Calendar.DAY_OF_MONTH, 1)
                val end = calendar.timeInMillis
                start to end
            }

            "This Month" -> {
                calendar.set(Calendar.DAY_OF_MONTH, 1)
                calendar.set(Calendar.HOUR_OF_DAY, 0)
                calendar.set(Calendar.MINUTE, 0)
                calendar.set(Calendar.SECOND, 0)
                calendar.set(Calendar.MILLISECOND, 0)
                val start = calendar.timeInMillis

                calendar.add(Calendar.MONTH, 1)
                val end = calendar.timeInMillis
                start to end
            }

            "This year" -> {
                calendar.set(Calendar.MONTH, Calendar.JANUARY)
                calendar.set(Calendar.DAY_OF_MONTH, 1)
                calendar.set(Calendar.HOUR_OF_DAY, 0)
                calendar.set(Calendar.MINUTE, 0)
                calendar.set(Calendar.SECOND, 0)
                calendar.set(Calendar.MILLISECOND, 0)
                val start = calendar.timeInMillis

                calendar.add(Calendar.YEAR, 1)
                val end = calendar.timeInMillis
                start to end
            }

            else -> null // Any time
        }
    }
}


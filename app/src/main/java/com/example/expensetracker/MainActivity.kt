package com.example.expensetracker

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.expensetracker.adaper.CategoryAdapter
import com.example.expensetracker.adaper.ExpenseAdapter
import com.example.expensetracker.adaper.ExpensePagingAdapter
import com.example.expensetracker.data.ExpenseData
import com.example.expensetracker.databinding.ActivityMainBinding
import com.example.expensetracker.helper.NightModManager
import com.example.expensetracker.viewmodel.ExpenseViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Calendar


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var viewModel: ExpenseViewModel
    lateinit var adapter: ExpenseAdapter
    lateinit var catAdapter: CategoryAdapter
    var dialog = ExpenseDialog()
    private val catList=listOf("All","Food","Shopping","Entertainment","Health","Other")

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        NightModManager.init(this)
        viewModel = ExpenseViewModel(application)


//        val dates = resources.getStringArray(R.array.durations)
//        val categories = resources.getStringArray(R.array.cat_items)
//
//        val arrayAdapter = ArrayAdapter(this, R.layout.dropdown_menu, dates)
//        binding.dateTV.setAdapter(arrayAdapter)
//        val arrayAdapter2 = ArrayAdapter(this, R.layout.dropdown_menu, categories)
//        binding.typeTV.setAdapter(arrayAdapter2)




        checkNightMode()
        //setupCategoryList()
        setupClicks()
        catList()
        setupRecyclerView()
        observeExpense()

    }

    private fun setupClicks() {
        binding.settingBTN.setOnClickListener {
            NightModManager.setNightModeEnabled(!NightModManager.isNightModeEnabled())
            checkNightMode()
        }

        binding.fabAdd.setOnClickListener {
            dialog = ExpenseDialog.newInstanceForAdd()
            dialog.show(supportFragmentManager, "expenseDialog")
        }
        binding.filterBTN.setOnClickListener {
            val intent = Intent(this, SettingActivity::class.java)
            startActivity(intent)
        }
    }


    private fun setupRecyclerView() {
        adapter= ExpenseAdapter(emptyList()) { position ->
            dialog = ExpenseDialog.newInstanceForEdit(position)
            dialog.show(supportFragmentManager, "expenseDialog")
        }
        binding.rvExpenses.adapter = adapter
        binding.rvExpenses.setHasFixedSize(true)
    }

    private fun handlerEmptyState(list: List<ExpenseData>) {
        if (list.isEmpty()) {
            binding.tvEmpty.visibility = TextView.VISIBLE
            binding.ivEmpty.visibility = ImageView.VISIBLE
            binding.expenseTV.visibility = TextView.GONE
            binding.filterBTN.visibility = TextView.GONE

        } else {
            binding.tvEmpty.visibility = TextView.GONE
            binding.ivEmpty.visibility = ImageView.GONE
            binding.expenseTV.visibility = TextView.VISIBLE
            binding.filterBTN.visibility = TextView.VISIBLE
        }
    }
    @SuppressLint("SetTextI18n")
    private fun updateTotal(list: List<ExpenseData>) {
        val total = list.sumOf { it.amount ?: 0.0 }
        binding.TotalAmountTV.text = "$total EGP"
    }

    private fun observeExpense(category: String?=null) {
        viewModel.expenses(category=category).observe(this@MainActivity) { it ->
            adapter.notifyItemChange(it)
            handlerEmptyState(it)
            updateTotal(it)
        }
    }

    fun getAll(category: String? = null, start: Long? = null, end: Long? = null) {
        viewModel.expenses(category, start, end).observe(this@MainActivity) {
            adapter = ExpenseAdapter(it) { position ->
                dialog = ExpenseDialog.newInstanceForEdit(position)
                dialog.show(supportFragmentManager, "expenseDialog")
            }
            adapter.notifyItemChange(it)
            binding.rvExpenses.setHasFixedSize(true)
            binding.rvExpenses.adapter = adapter
            adapter.notifyItemChange(it)
            getTotal(it)
        }
    }

    @SuppressLint("SetTextI18n")
    fun getTotal(expenseData: List<ExpenseData>) {
        val totalPrice = expenseData.sumOf { it.amount ?: 0.0 }
        binding.TotalAmountTV.text = "$totalPrice EGP"
    }

    fun checkNightMode() {
        if (NightModManager.isNightModeEnabled()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            binding.settingBTN.setImageResource(R.drawable.icon_night)
            binding.settingBTN.setColorFilter(
                ContextCompat.getColor(this, R.color.mainText),
                PorterDuff.Mode.SRC_IN
            )

        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            binding.settingBTN.setImageResource(R.drawable.icon_sun)

            binding.settingBTN.setColorFilter(
                ContextCompat.getColor(this, R.color.mainText),
                PorterDuff.Mode.SRC_IN
            )
        }
    }

    fun getDateRange(filter: String): Pair<Long, Long>? {
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

            "This month" -> {
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
    fun catList(){
        catAdapter= CategoryAdapter(catList){text ->
            Toast.makeText(this,text, Toast.LENGTH_SHORT).show()
            if (text=="All"){
                observeExpense()
            }else{
                observeExpense(category = text)
            }
        }
        binding.categoryRV.adapter=catAdapter
    }
}


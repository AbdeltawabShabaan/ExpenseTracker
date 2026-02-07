package com.example.expensetracker

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.expensetracker.data.AppDataBase
import com.example.expensetracker.data.ExpenseData
import com.example.expensetracker.helper.Constance
import com.example.expensetracker.viewmodel.ExpenseViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ExpenseDialog : BottomSheetDialogFragment() {
    lateinit var viewModel: ExpenseViewModel
    lateinit var saveButton: TextView
    lateinit var cancelButton: TextView
    lateinit var etAmount: TextInputEditText
    lateinit var amountET: TextInputEditText

    lateinit var categoryTV: AutoCompleteTextView

    private var expenseId: Int? =null

    companion object {

        fun newInstanceForAdd(): ExpenseDialog {
            return ExpenseDialog()
        }

        fun newInstanceForEdit(id: Int): ExpenseDialog {
            return ExpenseDialog().apply {
                arguments = Bundle().apply {
                    putInt(Constance.ARG_EXPENSE_ID, id)
                }
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.expense_dialog, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        expenseId = if (arguments?.containsKey(Constance.ARG_EXPENSE_ID) == true) {
            arguments?.getInt(Constance.ARG_EXPENSE_ID)
        } else {
            null
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        etAmount = view.findViewById(R.id.amountET)
        amountET= view.findViewById(R.id.amountET)
        saveButton = view.findViewById(R.id.add_expense)
        cancelButton = view.findViewById(R.id.cancel_expense)
        viewModel = ExpenseViewModel(requireActivity().application)
        categoryTV = view.findViewById(R.id.autoCompleteTextView)

        checkUpdateById(expenseId)

        val categories=resources.getStringArray(R.array.cat_items)

        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_menu, categories)
        categoryTV.setAdapter(arrayAdapter)


        saveButton.setOnClickListener {
            val amountText = etAmount.text.toString().toDoubleOrNull()
            val categoryText =categoryTV.text.toString()
            val expenseData = ExpenseData(
                amount = amountText,
                category = categoryText
            )
            if (isValidate(categoryText, amountText)){
                return@setOnClickListener
            }else{
                if (expenseId!=null){
                    update(expenseId!!,categoryText,amountText)
                    etAmount.text = Editable.Factory.getInstance().newEditable("")
                    categoryTV.text = Editable.Factory.getInstance().newEditable("")
                    Toast.makeText(requireContext(), "Updated Successfully", Toast.LENGTH_SHORT).show()
                    dismiss()
                }else{
                    insert(expenseData)
                    etAmount.text = Editable.Factory.getInstance().newEditable("")
                    categoryTV.text = Editable.Factory.getInstance().newEditable("")
                    Toast.makeText(requireContext(), "expense added successfully", Toast.LENGTH_SHORT).show()
                    dismiss()
                }
            }

        }
        cancelButton.setOnClickListener {
            dismiss()
        }

    }
    fun insert(expenseData: ExpenseData){
        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.insertExpense(expenseData)
        }
    }
    fun update(id: Int, category: String, amount: Double?){
        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.updateById(id, category, amount)
        }
    }
    fun checkUpdateById(expenseId: Int?){
        expenseId?.let { id ->
            viewModel.expenseById(id).observe(viewLifecycleOwner) {
                etAmount.setText(it.amount.toString())
                categoryTV.setText(it.category)
            }
            saveButton.text = "Update"
        } ?: run {
            saveButton.text = "Save"
        }
    }

    fun isValidate(category: String, amount: Double?): Boolean {
        if (amount == null || category.isEmpty() ) {
            Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()

        }
        return false
    }
    override fun onDestroyView() {
        super.onDestroyView()
        expenseId = null
    }
}
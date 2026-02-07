package com.example.expensetracker

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.RadioButton
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.expensetracker.data.FilterExpense
import com.example.expensetracker.databinding.FilterDialogBinding
import com.example.expensetracker.helper.Constance
import com.example.expensetracker.viewmodel.FilterViewModel
import java.util.Calendar

class Filter: DialogFragment() {
    private lateinit var binding: FilterDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("FilterDialog", "Dialog created")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FilterDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Constance.init(requireContext())

        binding.applyTV.setOnClickListener {
            val selectedID=binding.filterRadioGroup.checkedRadioButtonId
            val selectedRB=binding.root.findViewById<RadioButton>(selectedID)
            val filter=selectedRB.text.toString()
            Constance.saveDate(filter)
            dismiss()
        }
    }

}
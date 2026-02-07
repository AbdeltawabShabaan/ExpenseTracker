package com.example.expensetracker.adaper

import android.annotation.SuppressLint
import android.app.Application
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.expensetracker.R
import com.example.expensetracker.data.ExpenseData
import com.example.expensetracker.helper.Constance
import com.example.expensetracker.viewmodel.ExpenseViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ExpenseAdapter(private var expenseList: List<ExpenseData>, private val onItemClick:(Int)-> Unit): RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ExpenseViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.expense_items, parent, false)
        return ExpenseViewHolder(view)
    }

    @SuppressLint("SetTextI18n", "UseCompatLoadingForDrawables")
    override fun onBindViewHolder(
        holder: ExpenseViewHolder,
        position: Int
    ) {
        val expense = expenseList[position]
        holder.expenseCat.text=expense.category
        holder.amount.text=expense.amount.toString() + "EGP"
        holder.expDate.text= Constance.dateFormated(expense.date)
        holder.image.setImageResource(R.drawable.icon_other)
        holder.editExpense.setOnClickListener {
            onItemClick(expense.id)
        }
        holder.deleteExpense.setOnClickListener {
            val viewmodel= ExpenseViewModel(Application())
            showDeleteWarming(holder){

                viewmodel.deleteExpense(expense)
            }
            notifyItemRemoved(position)
        }
        if (expense.isUpdate){
            holder.massageUpdated.visibility=View.VISIBLE
        }else{
            holder.massageUpdated.visibility=View.GONE
        }
        when (expense.category.trim().lowercase()) {
            "food" -> holder.image.setImageResource(R.drawable.icon_food)
            "transportation" -> holder.image.setImageResource(R.drawable.icon_transportation)
            "shopping" -> holder.image.setImageResource(R.drawable.icon_shopping)
            "entertainment" -> holder.image.setImageResource(R.drawable.icon_entertainment)
            "health" -> holder.image.setImageResource(R.drawable.icon_health)
            "other" -> holder.image.setImageResource(R.drawable.icon_other)
        }
        holder.amount.background=holder.itemView.context.getDrawable(R.drawable.fab_bg)
        holder.amount.setTextColor(ContextCompat.getColor(holder.itemView.context,R.color.purple))
    }

    override fun getItemCount(): Int {
        return expenseList.size
    }

    class ExpenseViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val expenseCat: TextView=itemView.findViewById(R.id.cat_name)
        val massageUpdated: TextView=itemView.findViewById(R.id.massageUpdated)
        val deleteExpense: TextView=itemView.findViewById(R.id.delete_expense)
        val editExpense: TextView=itemView.findViewById(R.id.edit_expense)

        val amount: TextView=itemView.findViewById(R.id.exp_amount)
        val expDate: TextView=itemView.findViewById(R.id.exp_date)
        val image: ImageView =itemView.findViewById(R.id.expense_iv)

    }
    fun showDeleteWarming(holder: ExpenseViewHolder, onConfirm: () -> Unit) {
        MaterialAlertDialogBuilder(holder.itemView.context)
            .setTitle("Delete Expense")
            .setMessage("Are you sure you want to delete this expense?")
            .setPositiveButton("Yes") { _, _ ->
                onConfirm()
            }
            .setNegativeButton("No", null).show()
    }
    @SuppressLint("NotifyDataSetChanged")
    fun notifyItemChange(newList: List<ExpenseData>){
        expenseList=newList
        notifyDataSetChanged()
    }
}
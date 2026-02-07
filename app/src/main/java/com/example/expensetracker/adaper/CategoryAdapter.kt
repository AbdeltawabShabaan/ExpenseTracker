package com.example.expensetracker.adaper

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.expensetracker.R

class CategoryAdapter(private val categories: List<String>,private val onItemSelected:(String)-> Unit) :
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {
        private var selectedPosition= 0
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.category_items, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: CategoryViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        val list=categories[position]
        holder.category.text=list
        holder.category.background=
            if (position==selectedPosition){
                ContextCompat.getDrawable(holder.itemView.context,R.drawable.btn_parple_bg)
            }else{
                ContextCompat.getDrawable(holder.itemView.context,R.drawable.unselected_category_bg)
            }
        holder.category.setTextColor(if (position==selectedPosition){
            ContextCompat.getColorStateList(holder.itemView.context,R.color.white)
        }else{
            ContextCompat.getColorStateList(holder.itemView.context,R.color.mainText)
        })

        holder.category.setOnClickListener {
            val oldPosition=selectedPosition
            selectedPosition=position
            notifyItemChanged(oldPosition)
            notifyItemChanged(selectedPosition)
            onItemSelected(list)

        }
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val category: TextView =itemView.findViewById(R.id.categoryTV)
    }

}
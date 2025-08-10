package com.ateeb.smartexpensetrackerzobaze.ui.expense_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.ateeb.smartexpensetrackerzobaze.databinding.ExpenseListRvItemBinding
import com.ateeb.smartexpensetrackerzobaze.domain.model.Expense
import javax.inject.Inject

typealias OnItemClick = (position: Int, uri: String) -> Unit

class ExpenseDetailsRVAdapter (private val onItemClick: OnItemClick) : RecyclerView.Adapter<ExpenseDetailsRVAdapter.ExpViewHolder>() {

    private var expenses = mutableListOf<Expense>()

    // Use DiffUtil for efficient updates
    fun updateList(newList: List<Expense>) {
        val diffCallback = ExpenseDetailsDiffCallback(expenses, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        expenses.clear()
        expenses.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }

    inner class ExpViewHolder(private val binding: ExpenseListRvItemBinding) : ViewHolder(binding.root) {
        fun bind(expense: Expense) {
            with(binding) {
                // Basic fields
                expenseTitle.text = expense.title
                expenseAmount.text = "₹${expense.amount}" // Format currency
                expenseCategory.text = expense.category

                // Handle optional notes with visibility
                if (expense.notes.isNullOrBlank()) {
                    expenseNotes.visibility = View.GONE
                } else {
                    expenseNotes.visibility = View.VISIBLE
                    expenseNotes.text = expense.notes
                }

                // Handle optional receipt indicator
                if (expense.imageUri != null) {
                    receiptIndicator.visibility = View.VISIBLE
                    receiptIndicator.setOnClickListener {
                        onItemClick(adapterPosition, expense.imageUri) // Using the setter version
                    }
                } else {
                    receiptIndicator.visibility = View.GONE
                    receiptIndicator.setOnClickListener(null) // Clear previous listener
                }

            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ExpenseDetailsRVAdapter.ExpViewHolder {
        val binding =
            ExpenseListRvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ExpViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ExpenseDetailsRVAdapter.ExpViewHolder, position: Int) {
        holder.bind(expenses[position])
    }

    override fun getItemCount(): Int = expenses.size

}
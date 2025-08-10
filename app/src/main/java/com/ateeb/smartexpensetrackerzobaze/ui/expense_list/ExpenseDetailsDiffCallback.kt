package com.ateeb.smartexpensetrackerzobaze.ui.expense_list

import androidx.recyclerview.widget.DiffUtil
import com.ateeb.smartexpensetrackerzobaze.domain.model.Expense

class ExpenseDetailsDiffCallback(
    private val oldList: List<Expense>,
    private val newList: List<Expense>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        // Compare content for changes
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}
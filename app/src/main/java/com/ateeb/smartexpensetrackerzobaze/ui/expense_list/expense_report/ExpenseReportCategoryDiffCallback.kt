package com.ateeb.smartexpensetrackerzobaze.ui.expense_list.expense_report

import androidx.recyclerview.widget.DiffUtil
import com.ateeb.smartexpensetrackerzobaze.domain.model.CategoryTotal
import com.ateeb.smartexpensetrackerzobaze.domain.model.Expense

class ExpenseReportCategoryDiffCallback(
    private val oldList: List<CategoryTotal>,
    private val newList: List<CategoryTotal>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].categoryName == newList[newItemPosition].categoryName
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        // Compare content for changes
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}
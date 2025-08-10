package com.ateeb.smartexpensetrackerzobaze.ui.expense_list.expense_report

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.ateeb.smartexpensetrackerzobaze.databinding.CategoryRvItemBinding
import com.ateeb.smartexpensetrackerzobaze.domain.model.CategoryTotal
import com.ateeb.smartexpensetrackerzobaze.domain.model.Expense
import com.ateeb.smartexpensetrackerzobaze.ui.expense_list.ExpenseDetailsDiffCallback

class ExpenseReportCategoryRVAdapter() :
    RecyclerView.Adapter<ExpenseReportCategoryRVAdapter.ExpViewHolder>() {

    companion object {
        // Category colors
        val CATEGORY_COLORS = mapOf(
            "Staff" to "#FF6B6B",
            "Travel" to "#4ECDC4",
            "Food" to "#45B7D1",
            "Utility" to "#96CEB4"
        )
    }

    private var categories = mutableListOf<CategoryTotal>()

    // Use DiffUtil for efficient updates
    fun updateList(newList: List<CategoryTotal>) {
        val diffCallback = ExpenseReportCategoryDiffCallback(categories, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        categories.clear()
        categories.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }

    inner class ExpViewHolder(private val binding: CategoryRvItemBinding) :
        ViewHolder(binding.root) {
        fun bind(categoryTotal: CategoryTotal) {
            binding.apply {
                // Set category name
                categoryName.text = categoryTotal.categoryName

                // Format and set amount (no decimals for clean look)
                categoryAmount.text = "₹${String.format("%.0f", categoryTotal.totalAmount)}"

                // Set progress bar percentage
                categoryProgressBar.progress = categoryTotal.percentage.toInt()

                // Set progress bar color based on category
                val color = CATEGORY_COLORS[categoryTotal.categoryName] ?: "#757575"
                categoryProgressBar.progressTintList = ColorStateList.valueOf(
                    Color.parseColor(color)
                )

            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ExpenseReportCategoryRVAdapter.ExpViewHolder {
        val binding =
            CategoryRvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ExpViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ExpenseReportCategoryRVAdapter.ExpViewHolder,
        position: Int
    ) {
        holder.bind(categories[position])
    }

    override fun getItemCount(): Int = categories.size

}
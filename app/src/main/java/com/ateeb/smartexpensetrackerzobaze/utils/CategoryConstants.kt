package com.ateeb.smartexpensetrackerzobaze.utils

import com.ateeb.smartexpensetrackerzobaze.domain.model.CategoryTotal
import com.ateeb.smartexpensetrackerzobaze.domain.model.Expense
import com.ateeb.smartexpensetrackerzobaze.ui.expense_list.expense_report.ExpenseReportCategoryRVAdapter

object CategoryConstants {
    val CATEGORY_LIST = listOf("Staff", "Travel", "Food", "Utility")

    fun calculateCategoryTotals(expenses: List<Expense>): List<CategoryTotal> {
        val categoryList = CATEGORY_LIST
        val totalAmount = expenses.sumOf { it.amount }

        return categoryList.mapNotNull { category ->
            val categoryExpenses = expenses.filter { it.category == category }
            val categoryAmount = categoryExpenses.sumOf { it.amount }

            // Only include categories that have expenses
            if (categoryAmount > 0) {
                CategoryTotal(
                    categoryName = category,
                    totalAmount = categoryAmount,
                    expenseCount = categoryExpenses.size,
                    percentage = if (totalAmount > 0) (categoryAmount / totalAmount * 100).toFloat() else 0f,
                    color = ExpenseReportCategoryRVAdapter.CATEGORY_COLORS[category] ?: "#757575"

                )
            } else {
                null
            }
        }.sortedByDescending { it.totalAmount } // Sort by highest amount first
    }
}
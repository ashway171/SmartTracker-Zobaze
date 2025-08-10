package com.ateeb.smartexpensetrackerzobaze.domain.repository

import com.ateeb.smartexpensetrackerzobaze.domain.model.Expense
import com.ateeb.smartexpensetrackerzobaze.domain.model.ExpenseSummary
import kotlinx.coroutines.flow.Flow
import java.util.Date

interface ExpenseRepository {
    suspend fun saveExpense(expense: Expense)
    suspend fun getExpensesByDate(startDate: Date, endDate: Date): Flow<List<Expense>>
    suspend fun getExpenseSummary(): Flow<ExpenseSummary>
    suspend fun getExpensesByDateAndCategory(
        startDate: Date,
        endDate: Date,
        category: String
    ): Flow<List<Expense>>
}
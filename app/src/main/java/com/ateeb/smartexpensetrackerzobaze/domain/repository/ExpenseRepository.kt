package com.ateeb.smartexpensetrackerzobaze.domain.repository

import com.ateeb.smartexpensetrackerzobaze.domain.model.Expense

interface ExpenseRepository {
    suspend fun saveExpense(expense: Expense)
}
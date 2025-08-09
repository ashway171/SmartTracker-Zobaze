package com.ateeb.smartexpensetrackerzobaze.domain.usecase

import com.ateeb.smartexpensetrackerzobaze.domain.model.Expense
import com.ateeb.smartexpensetrackerzobaze.domain.repository.ExpenseRepository
import javax.inject.Inject

class SaveExpenseUseCase @Inject constructor(private val repository: ExpenseRepository) {
    // Overload operator.. treat this usecase like a fun
    suspend operator fun invoke(expense: Expense) {
        repository.saveExpense(expense)
    }
}
package com.ateeb.smartexpensetrackerzobaze.domain.usecase

import com.ateeb.smartexpensetrackerzobaze.domain.model.Expense
import com.ateeb.smartexpensetrackerzobaze.domain.repository.ExpenseRepository
import kotlinx.coroutines.flow.Flow
import java.util.Date
import javax.inject.Inject

class GetExpensesByCategoryAndDateUseCase @Inject constructor(private val repository: ExpenseRepository) {
    suspend operator fun invoke(
        startDate: Date,
        endDate: Date,
        category: String
    ): Flow<List<Expense>> {
        return repository.getExpensesByDateAndCategory(startDate, endDate, category)
    }
}
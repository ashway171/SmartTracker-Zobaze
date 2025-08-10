package com.ateeb.smartexpensetrackerzobaze.domain.usecase

import com.ateeb.smartexpensetrackerzobaze.domain.model.ExpenseSummary
import com.ateeb.smartexpensetrackerzobaze.domain.repository.ExpenseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetExpenseSummaryUseCase @Inject constructor(private val expenseRepository: ExpenseRepository) {
    suspend operator fun invoke(): Flow<ExpenseSummary> {
        return expenseRepository.getExpenseSummary()
    }
}
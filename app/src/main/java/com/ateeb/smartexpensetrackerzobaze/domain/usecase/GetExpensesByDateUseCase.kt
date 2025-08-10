package com.ateeb.smartexpensetrackerzobaze.domain.usecase

import com.ateeb.smartexpensetrackerzobaze.domain.model.Expense
import com.ateeb.smartexpensetrackerzobaze.domain.repository.ExpenseRepository
import kotlinx.coroutines.flow.Flow
import java.util.Date
import javax.inject.Inject

class GetExpensesByDateUseCase @Inject constructor(private val expenseRepository: ExpenseRepository) {
    suspend operator fun invoke(startDate: Date, endDate: Date) : Flow<List<Expense>>{
        return expenseRepository.getExpensesByDate(startDate, endDate)
    }
}
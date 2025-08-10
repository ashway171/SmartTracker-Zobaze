package com.ateeb.smartexpensetrackerzobaze.data.repository

import com.ateeb.smartexpensetrackerzobaze.data.local.ExpenseDao
import com.ateeb.smartexpensetrackerzobaze.data.local.ExpenseEntity
import com.ateeb.smartexpensetrackerzobaze.data.local.ExpenseSummaryEntity
import com.ateeb.smartexpensetrackerzobaze.domain.model.Expense
import com.ateeb.smartexpensetrackerzobaze.domain.model.ExpenseSummary
import com.ateeb.smartexpensetrackerzobaze.domain.repository.ExpenseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.util.Date
import javax.inject.Inject

class ExpenseRepositoryImpl @Inject constructor(private val dao: ExpenseDao) : ExpenseRepository {
    override suspend fun saveExpense(expense: Expense) {
        // Called from viewModelScope.launch{} [Dispatchers.Main.Immediate by default]
        withContext(Dispatchers.IO) {
            val entity = expense.toEntity()
            dao.insertExpense(entity)
        }
    }

    override suspend fun getExpensesByDate(startDate: Date, endDate: Date): Flow<List<Expense>> {
        return withContext(Dispatchers.IO) {
            dao.getExpensesByDate(startDate, endDate).map { list -> list.map { it.toDomain() } }
        }
    }

    override suspend fun getExpenseSummary(): Flow<ExpenseSummary> {
        return withContext(Dispatchers.IO) {
            dao.getExpenseSummary()
                .map { it.toDomain() }
        }
    }

    override suspend fun getExpensesByDateAndCategory(
        startDate: Date,
        endDate: Date,
        category: String
    ): Flow<List<Expense>> {
        return withContext(Dispatchers.IO) {
            dao.getExpensesByDateAndCategory(startDate, endDate, category)
                .map { list -> list.map { it.toDomain() } }
        }
    }
}

// Mapping extensions
fun ExpenseEntity.toDomain(): Expense = Expense(
    id = id,
    title = title,
    amount = amount,
    notes = notes,
    category = category,
    imageUri = imageUri,
    date = date
)

fun Expense.toEntity(): ExpenseEntity = ExpenseEntity(
    id = id,
    title = title,
    amount = amount,
    notes = notes,
    category = category,
    imageUri = imageUri,
    date = date
)

fun ExpenseSummaryEntity.toDomain(): ExpenseSummary {
    return ExpenseSummary(
        totalItems = totalItems,
        totalAmount = totalAmount
    )
}
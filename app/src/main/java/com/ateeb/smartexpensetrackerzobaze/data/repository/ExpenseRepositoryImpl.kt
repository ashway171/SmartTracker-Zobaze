package com.ateeb.smartexpensetrackerzobaze.data.repository

import com.ateeb.smartexpensetrackerzobaze.data.local.ExpenseDao
import com.ateeb.smartexpensetrackerzobaze.data.local.ExpenseEntity
import com.ateeb.smartexpensetrackerzobaze.domain.model.Expense
import com.ateeb.smartexpensetrackerzobaze.domain.repository.ExpenseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ExpenseRepositoryImpl @Inject constructor(private val dao: ExpenseDao) : ExpenseRepository {


    override suspend fun saveExpense(expense: Expense) {
        // Called from viewModelScope.launch{} [Dispatchers.Main.Immediate by default]
        withContext(Dispatchers.IO) {
            val entity = expense.toEntity()
            dao.insertExpense(entity)
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
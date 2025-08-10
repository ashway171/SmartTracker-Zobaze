package com.ateeb.smartexpensetrackerzobaze.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ateeb.smartexpensetrackerzobaze.domain.model.ExpenseSummary
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface ExpenseDao {
    @Insert
    fun insertExpense(expense: ExpenseEntity)

    @Query("SELECT * FROM expenses WHERE date >= :startDate AND date < :endDate")
    fun getExpensesByDate(startDate: Date, endDate: Date): Flow<List<ExpenseEntity>>

    @Query(
        "SELECT COUNT(*) AS totalItems, IFNULL(SUM(amount), 0) AS totalAmount FROM expenses"
    )
    fun getExpenseSummary(): Flow<ExpenseSummaryEntity>

    @Query("SELECT * FROM expenses WHERE date >= :startDate AND date < :endDate AND category = :category")
    fun getExpensesByDateAndCategory(startDate: Date, endDate: Date, category: String): Flow<List<ExpenseEntity>>


}
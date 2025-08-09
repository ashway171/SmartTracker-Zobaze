package com.ateeb.smartexpensetrackerzobaze.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {
    @Insert
    fun insertExpense(expense: ExpenseEntity)

}
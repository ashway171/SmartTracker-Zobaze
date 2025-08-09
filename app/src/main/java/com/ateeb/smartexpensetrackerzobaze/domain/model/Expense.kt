package com.ateeb.smartexpensetrackerzobaze.domain.model

import java.util.Date

data class Expense(
    val id: Long = 0,
    val title: String,
    val amount: Double,
    val notes: String?,
    val category: String,
    val imageUri: String?,
    val date: Date
)

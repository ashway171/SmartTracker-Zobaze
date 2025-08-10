package com.ateeb.smartexpensetrackerzobaze.domain.model

data class CategoryTotal(
    val categoryName: String,
    val totalAmount: Double,
    val expenseCount: Int,
    val percentage: Float,
    val color: String
)

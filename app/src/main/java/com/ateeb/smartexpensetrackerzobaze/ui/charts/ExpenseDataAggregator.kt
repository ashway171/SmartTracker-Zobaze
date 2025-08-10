package com.ateeb.smartexpensetrackerzobaze.ui.charts

import com.ateeb.smartexpensetrackerzobaze.domain.model.Expense
import com.github.mikephil.charting.data.BarEntry
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class ExpenseDataAggregator<T : Expense>(
    private val dateFormat: SimpleDateFormat = SimpleDateFormat("dd MMM", Locale.getDefault()),
    private val daysToAggregate: Int = 7
) {

    fun aggregateLastNDaysTotals(expenses: List<T>): Pair<List<BarEntry>, List<String>> {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        val dates = mutableListOf<String>()
        val dailyTotals = mutableMapOf<String, Double>()

        for (i in daysToAggregate - 1 downTo 0) {
            val dayCal = calendar.clone() as Calendar
            dayCal.add(Calendar.DAY_OF_YEAR, -i)
            val dateKey = dateFormat.format(dayCal.time)
            dates.add(dateKey)
            dailyTotals[dateKey] = 0.0
        }

        expenses.forEach {
            val expenseDateKey = dateFormat.format(it.date)
            if (expenseDateKey in dailyTotals) {
                dailyTotals[expenseDateKey] = dailyTotals[expenseDateKey]!! + it.amount
            }
        }

        val entries = dailyTotals.values.mapIndexed { index, amount ->
            BarEntry(index.toFloat(), amount.toFloat())
        }

        return Pair(entries, dates)
    }
}

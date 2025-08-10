package com.ateeb.smartexpensetrackerzobaze.ui.charts

import android.graphics.Color
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter

class BarChartRenderer(
    private val barChart: BarChart,
    private val barColor: Int = Color.parseColor("#4ECDC4"),
    private val valueTextSize: Float = 12f
) {

    fun renderChart(entries: List<BarEntry>, labels: List<String>, labelForDataSet: String = "Expenses") {
        val barDataSet = BarDataSet(entries, labelForDataSet).apply {
            color = barColor
            valueTextSize = this@BarChartRenderer.valueTextSize
            valueFormatter = object : ValueFormatter() {
                override fun getFormattedValue(value: Float): String {
                    return "₹${value.toInt()}"
                }
            }
        }

        barChart.data = BarData(barDataSet)

        val xAxis = barChart.xAxis
        xAxis.valueFormatter = IndexAxisValueFormatter(labels)
        xAxis.granularity = 1f
        xAxis.setDrawGridLines(false)
        xAxis.position = com.github.mikephil.charting.components.XAxis.XAxisPosition.BOTTOM
        xAxis.textSize = valueTextSize

        barChart.axisLeft.apply {
            axisMinimum = 0f
            textSize = valueTextSize
        }
        barChart.axisRight.isEnabled = false

        barChart.description.isEnabled = false

        barChart.animateY(1000)
        barChart.invalidate()
    }
}

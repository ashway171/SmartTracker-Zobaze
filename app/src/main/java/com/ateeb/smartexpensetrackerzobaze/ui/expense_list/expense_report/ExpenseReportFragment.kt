package com.ateeb.smartexpensetrackerzobaze.ui.expense_list.expense_report

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.ateeb.smartexpensetrackerzobaze.R
import com.ateeb.smartexpensetrackerzobaze.databinding.FragmentExpenseReportBinding
import com.ateeb.smartexpensetrackerzobaze.domain.model.CategoryTotal
import com.ateeb.smartexpensetrackerzobaze.domain.model.Expense
import com.ateeb.smartexpensetrackerzobaze.ui.charts.BarChartRenderer
import com.ateeb.smartexpensetrackerzobaze.ui.charts.ExpenseDataAggregator
import com.ateeb.smartexpensetrackerzobaze.utils.CategoryConstants
import com.ateeb.smartexpensetrackerzobaze.utils.ExpenseConstants
import com.ateeb.smartexpensetrackerzobaze.utils.ExpenseConstants.getMockExpenseData
import com.github.mikephil.charting.charts.BarChart

class ExpenseReportFragment : Fragment() {

    private var _binding: FragmentExpenseReportBinding? = null
    private val binding get() = _binding!!

    private lateinit var categoriesAdapter: ExpenseReportCategoryRVAdapter
    private var categoriesList: MutableList<CategoryTotal> = mutableListOf()

    private lateinit var barChartRenderer: BarChartRenderer
    private lateinit var dataAggregator: ExpenseDataAggregator<Expense>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExpenseReportBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val barChart = view.findViewById<BarChart>(R.id.barChart)
        barChartRenderer = BarChartRenderer(barChart)
        dataAggregator = ExpenseDataAggregator()

        val expenses = getMockExpenseData()

        val (entries, labels) = dataAggregator.aggregateLastNDaysTotals(expenses)
        barChartRenderer.renderChart(entries, labels)

        setupCategoriesRVAdapter()
        categoriesList =
            CategoryConstants.calculateCategoryTotals(ExpenseConstants.getMockExpenseData())
                .toMutableList()

        categoriesAdapter.updateList(categoriesList)
    }

    private fun setupCategoriesRVAdapter() {
        // Set the click listener on the injected adapter
        categoriesAdapter = ExpenseReportCategoryRVAdapter()

        // Setup RecyclerView
        binding.rvCategoryTotals.apply {
            adapter = this@ExpenseReportFragment.categoriesAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
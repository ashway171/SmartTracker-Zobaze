package com.ateeb.smartexpensetrackerzobaze.ui.expense_list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ateeb.smartexpensetrackerzobaze.domain.model.Expense
import com.ateeb.smartexpensetrackerzobaze.domain.model.ExpenseSummary
import com.ateeb.smartexpensetrackerzobaze.domain.usecase.GetExpenseSummaryUseCase
import com.ateeb.smartexpensetrackerzobaze.domain.usecase.GetExpensesByCategoryAndDateUseCase
import com.ateeb.smartexpensetrackerzobaze.domain.usecase.GetExpensesByDateUseCase
import com.ateeb.smartexpensetrackerzobaze.ui.base.UiState
import com.ateeb.smartexpensetrackerzobaze.utils.DateUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

class ExpenseListViewModel @Inject constructor(
    private val getExpensesByDateUseCase: GetExpensesByDateUseCase,
    private val getExpenseSummaryUseCase: GetExpenseSummaryUseCase,
    private val getExpensesByCategoryAndDateUseCase: GetExpensesByCategoryAndDateUseCase
) : ViewModel() {

    var selectedCategory: String = "All"

    private val _expenses = MutableStateFlow<UiState<List<Expense>>>(UiState.Idle)
    val expenses: StateFlow<UiState<List<Expense>>> = _expenses.asStateFlow()

    private val _expenseSummary = MutableStateFlow<UiState<ExpenseSummary>>(UiState.Idle)
    val expenseSummary: StateFlow<UiState<ExpenseSummary>> = _expenseSummary.asStateFlow()

    init{
        val (startOfDay, endOfDay) = DateUtils.getTodayStartAndEndDates()
        loadExpensesByDate(startOfDay, endOfDay)
        loadExpenseSummary()
    }

    fun loadExpensesByDate(startDate: Date, endDate: Date) {
        viewModelScope.launch {
            _expenses.value = UiState.Loading
            getExpensesByDateUseCase(startDate, endDate)
                .catch { e -> _expenses.value = UiState.Error(e.message ?: "Unknown Error") }
                .collect { expensesList ->
                    Log.d("FragmentViewModel", expensesList.toString())
                    _expenses.value = UiState.Success(expensesList)
                }
        }
    }

    private fun loadExpenseSummary() {
        viewModelScope.launch {
            _expenseSummary.value = UiState.Loading
            getExpenseSummaryUseCase()
                .catch { e -> _expenseSummary.value = UiState.Error(e.message ?: "Unknown Error") }
                .collect { summary ->
                    _expenseSummary.value = UiState.Success(summary)
                }
        }
    }

    fun loadExpensesByDateAndCategory(startDate: Date, endDate: Date, category: String) {
        viewModelScope.launch {
            _expenses.value = UiState.Loading
            getExpensesByCategoryAndDateUseCase(startDate, endDate, category)
                .catch { e -> _expenses.value = UiState.Error(e.message ?: "Unknown Error") }
                .collect { expensesList ->
                    Log.d("FragmentViewModel", expensesList.toString())
                    _expenses.value = UiState.Success(expensesList)
                }
        }
    }

}
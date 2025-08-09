package com.ateeb.smartexpensetrackerzobaze.ui.expense_entry

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ateeb.smartexpensetrackerzobaze.domain.model.Expense
import com.ateeb.smartexpensetrackerzobaze.domain.usecase.SaveExpenseUseCase
import com.ateeb.smartexpensetrackerzobaze.ui.base.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SaveExpenseViewModel @Inject constructor(private val saveExpenseUseCase: SaveExpenseUseCase) : ViewModel() {

    private val _saveExpenseState = MutableStateFlow<UiState<Boolean>?>(UiState.Idle)
    val saveExpenseState: StateFlow<UiState<Boolean>?> = _saveExpenseState.asStateFlow()

    fun saveExpense(expense: Expense) {
        viewModelScope.launch {
            _saveExpenseState.value = UiState.Loading
            try {
                saveExpenseUseCase(expense)
                _saveExpenseState.value = UiState.Success(true)
            } catch (e: Exception) {
                e.printStackTrace()
                _saveExpenseState.value = UiState.Error(e.message ?: "Unrecorded Error")
            }
        }
    }

}
package com.ateeb.smartexpensetrackerzobaze.di.module

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ateeb.smartexpensetrackerzobaze.domain.usecase.GetExpenseSummaryUseCase
import com.ateeb.smartexpensetrackerzobaze.domain.usecase.GetExpensesByCategoryAndDateUseCase
import com.ateeb.smartexpensetrackerzobaze.domain.usecase.GetExpensesByDateUseCase
import com.ateeb.smartexpensetrackerzobaze.ui.base.ViewModelProviderFactory
import com.ateeb.smartexpensetrackerzobaze.ui.expense_entry.SaveExpenseViewModel
import com.ateeb.smartexpensetrackerzobaze.ui.expense_list.ExpenseDetailsRVAdapter
import com.ateeb.smartexpensetrackerzobaze.ui.expense_list.ExpenseListViewModel
import com.ateeb.smartexpensetrackerzobaze.ui.expense_list.OnItemClick
import dagger.Module
import dagger.Provides

@Module
class FragmentModule(private val fragment: Fragment) {

    @Provides
    fun provideContext(): Context = fragment.requireContext()

    @Provides
    fun provideExpenseListViewModel(
        getExpensesByDateUseCase: GetExpensesByDateUseCase,
        getExpenseSummaryUseCase: GetExpenseSummaryUseCase,
        getExpensesByCategoryAndDateUseCase: GetExpensesByCategoryAndDateUseCase
    ): ExpenseListViewModel {
        return ViewModelProvider(
            fragment.requireActivity(),
            ViewModelProviderFactory(ExpenseListViewModel::class) {
                ExpenseListViewModel(getExpensesByDateUseCase, getExpenseSummaryUseCase, getExpensesByCategoryAndDateUseCase)
            }
        )[ExpenseListViewModel::class.java]
    }
}
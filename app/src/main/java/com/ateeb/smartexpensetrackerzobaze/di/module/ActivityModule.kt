package com.ateeb.smartexpensetrackerzobaze.di.module

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.ateeb.smartexpensetrackerzobaze.di.ActivityContext
import com.ateeb.smartexpensetrackerzobaze.domain.usecase.GetExpenseSummaryUseCase
import com.ateeb.smartexpensetrackerzobaze.domain.usecase.GetExpensesByCategoryAndDateUseCase
import com.ateeb.smartexpensetrackerzobaze.domain.usecase.GetExpensesByDateUseCase
import com.ateeb.smartexpensetrackerzobaze.domain.usecase.SaveExpenseUseCase
import com.ateeb.smartexpensetrackerzobaze.ui.base.ViewModelProviderFactory
import com.ateeb.smartexpensetrackerzobaze.ui.expense_entry.SaveExpenseViewModel
import com.ateeb.smartexpensetrackerzobaze.ui.expense_list.ExpenseListViewModel
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val activity: AppCompatActivity) {

    @ActivityContext
    @Provides
    fun provideContext(): Context = this.activity

    @Provides
    fun provideSaveExpenseViewModel(
        saveExpenseUseCase: SaveExpenseUseCase
    ): SaveExpenseViewModel {
        return ViewModelProvider(
            activity,
            ViewModelProviderFactory(SaveExpenseViewModel::class) {
                SaveExpenseViewModel(saveExpenseUseCase)
            }
        )[SaveExpenseViewModel::class.java]
    }

    @Provides
    fun provideExpenseListViewModel(
        getExpensesByDateUseCase: GetExpensesByDateUseCase,
        getExpenseSummaryUseCase: GetExpenseSummaryUseCase,
        getExpensesByCategoryAndDateUseCase: GetExpensesByCategoryAndDateUseCase
    ): ExpenseListViewModel {
        return ViewModelProvider(
            activity,
            ViewModelProviderFactory(ExpenseListViewModel::class) {
                ExpenseListViewModel(getExpensesByDateUseCase, getExpenseSummaryUseCase, getExpensesByCategoryAndDateUseCase)
            }
        )[ExpenseListViewModel::class.java]
    }

}


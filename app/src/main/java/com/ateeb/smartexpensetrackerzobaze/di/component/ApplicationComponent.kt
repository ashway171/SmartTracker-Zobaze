package com.ateeb.smartexpensetrackerzobaze.di.component

import com.ateeb.smartexpensetrackerzobaze.MainApplication
import com.ateeb.smartexpensetrackerzobaze.data.local.AppDatabase
import com.ateeb.smartexpensetrackerzobaze.data.local.ExpenseDao
import com.ateeb.smartexpensetrackerzobaze.di.module.ApplicationModule
import com.ateeb.smartexpensetrackerzobaze.domain.repository.ExpenseRepository
import com.ateeb.smartexpensetrackerzobaze.domain.usecase.GetExpenseSummaryUseCase
import com.ateeb.smartexpensetrackerzobaze.domain.usecase.GetExpensesByCategoryAndDateUseCase
import com.ateeb.smartexpensetrackerzobaze.domain.usecase.GetExpensesByDateUseCase
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun inject(application: MainApplication)

    fun provideDatabase(): AppDatabase
    fun provideYourDao(): ExpenseDao
    fun provideExpenseRepository(): ExpenseRepository
    fun provideGetExpensesByDateUseCase(): GetExpensesByDateUseCase
    fun provideGetExpenseSummaryUseCase(): GetExpenseSummaryUseCase
    fun provideGetExpensesByCategoryAndDateUseCase(): GetExpensesByCategoryAndDateUseCase

}
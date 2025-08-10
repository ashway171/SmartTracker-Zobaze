package com.ateeb.smartexpensetrackerzobaze.di.module

import android.content.Context
import androidx.room.Room
import com.ateeb.smartexpensetrackerzobaze.MainApplication
import com.ateeb.smartexpensetrackerzobaze.data.local.AppDatabase
import com.ateeb.smartexpensetrackerzobaze.data.local.ExpenseDao
import com.ateeb.smartexpensetrackerzobaze.data.repository.ExpenseRepositoryImpl
import com.ateeb.smartexpensetrackerzobaze.di.ApplicationContext
import com.ateeb.smartexpensetrackerzobaze.domain.repository.ExpenseRepository
import com.ateeb.smartexpensetrackerzobaze.domain.usecase.GetExpenseSummaryUseCase
import com.ateeb.smartexpensetrackerzobaze.domain.usecase.GetExpensesByCategoryAndDateUseCase
import com.ateeb.smartexpensetrackerzobaze.domain.usecase.GetExpensesByDateUseCase
import com.ateeb.smartexpensetrackerzobaze.domain.usecase.SaveExpenseUseCase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: MainApplication) {

    @ApplicationContext
    @Provides
    fun provideContext(): Context {
        return application
    }

    @Provides
    @Singleton
    fun provideDatabase(): AppDatabase {
        return Room.databaseBuilder(
            application,
            AppDatabase::class.java,
            "expense_tracker_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideExpenseDao(db: AppDatabase): ExpenseDao {
        return db.expenseDao()
    }

    @Provides
    @Singleton
    fun provideExpenseRepository(
        expenseDao: ExpenseDao
    ): ExpenseRepository {
        return ExpenseRepositoryImpl(expenseDao)
    }

    @Provides
    @Singleton
    fun provideSaveExpenseUseCase(
        expenseRepo: ExpenseRepository
    ): SaveExpenseUseCase {
        return SaveExpenseUseCase(expenseRepo)
    }

    @Provides
    @Singleton
    fun provideGetExpensesByDateUseCase(
        expenseRepo: ExpenseRepository
    ): GetExpensesByDateUseCase {
        return GetExpensesByDateUseCase(expenseRepo)
    }

    @Provides
    @Singleton
    fun provideGetExpenseSummaryUseCase(
        expenseRepo: ExpenseRepository
    ): GetExpenseSummaryUseCase {
        return GetExpenseSummaryUseCase(expenseRepo)
    }

    @Provides
    @Singleton
    fun provideGetExpensesByCategoryAndDateUseCase(
        expenseRepo: ExpenseRepository
    ): GetExpensesByCategoryAndDateUseCase {
        return GetExpensesByCategoryAndDateUseCase(expenseRepo)
    }

}
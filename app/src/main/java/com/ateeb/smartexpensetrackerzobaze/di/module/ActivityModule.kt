package com.ateeb.smartexpensetrackerzobaze.di.module

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.ateeb.smartexpensetrackerzobaze.di.ActivityContext
import com.ateeb.smartexpensetrackerzobaze.domain.usecase.SaveExpenseUseCase
import com.ateeb.smartexpensetrackerzobaze.ui.expense_entry.SaveExpenseViewModel
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
        return SaveExpenseViewModel(saveExpenseUseCase)
    }

}


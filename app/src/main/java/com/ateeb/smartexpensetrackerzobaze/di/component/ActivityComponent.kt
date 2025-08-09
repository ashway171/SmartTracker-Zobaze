package com.ateeb.smartexpensetrackerzobaze.di.component

import com.ateeb.smartexpensetrackerzobaze.ui.MainActivity
import com.ateeb.smartexpensetrackerzobaze.di.ActivityScope
import com.ateeb.smartexpensetrackerzobaze.di.module.ActivityModule
import com.ateeb.smartexpensetrackerzobaze.ui.expense_entry.ExpenseEntryActivity
import dagger.Component

@ActivityScope
@Component(dependencies = [ApplicationComponent::class], modules = [ActivityModule::class])
interface ActivityComponent {

    fun injectMainActivity(activity: MainActivity)
    fun injectExpenseEntryActivity(activity: ExpenseEntryActivity)

}
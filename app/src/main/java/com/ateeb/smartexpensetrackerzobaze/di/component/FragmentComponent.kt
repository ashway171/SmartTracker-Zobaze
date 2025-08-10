package com.ateeb.smartexpensetrackerzobaze.di.component

import com.ateeb.smartexpensetrackerzobaze.di.FragmentScope
import com.ateeb.smartexpensetrackerzobaze.di.module.FragmentModule
import com.ateeb.smartexpensetrackerzobaze.ui.expense_list.ExpenseDetailsFragment
import dagger.Component

@FragmentScope
@Component(dependencies = [ApplicationComponent::class], modules = [FragmentModule::class])
interface FragmentComponent {
    fun inject(fragment: ExpenseDetailsFragment)
}
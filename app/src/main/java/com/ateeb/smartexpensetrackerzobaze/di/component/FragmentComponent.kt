package com.ateeb.smartexpensetrackerzobaze.di.component

import com.ateeb.smartexpensetrackerzobaze.di.FragmentScope
import com.ateeb.smartexpensetrackerzobaze.di.module.FragmentModule
import dagger.Component

@FragmentScope
@Component(dependencies = [ApplicationComponent::class], modules = [FragmentModule::class])
interface FragmentComponent {

}
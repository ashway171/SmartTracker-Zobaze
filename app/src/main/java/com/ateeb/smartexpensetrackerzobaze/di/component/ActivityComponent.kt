package com.ateeb.smartexpensetrackerzobaze.di.component

import com.ateeb.smartexpensetrackerzobaze.MainActivity
import com.ateeb.smartexpensetrackerzobaze.di.ActivityScope
import com.ateeb.smartexpensetrackerzobaze.di.module.ActivityModule
import dagger.Component

@ActivityScope
@Component(dependencies = [ApplicationComponent::class], modules = [ActivityModule::class])
interface ActivityComponent {

    fun injectMainActivity(activity: MainActivity)

}
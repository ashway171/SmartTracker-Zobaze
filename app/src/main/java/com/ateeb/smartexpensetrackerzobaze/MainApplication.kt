package com.ateeb.smartexpensetrackerzobaze

import android.app.Application
import com.ateeb.smartexpensetrackerzobaze.di.component.ApplicationComponent
import com.ateeb.smartexpensetrackerzobaze.di.component.DaggerApplicationComponent
import com.ateeb.smartexpensetrackerzobaze.di.module.ApplicationModule

class MainApplication : Application() {

    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        injectDependencies()
    }

    private fun injectDependencies() {
        applicationComponent = DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .build()
        applicationComponent.inject(this)

    }
}
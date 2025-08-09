package com.ateeb.smartexpensetrackerzobaze.di.module

import android.content.Context
import com.ateeb.smartexpensetrackerzobaze.MainApplication
import com.ateeb.smartexpensetrackerzobaze.di.ApplicationContext
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule(private val application: MainApplication) {

    @ApplicationContext
    @Provides
    fun provideContext(): Context {
        return application
    }
}
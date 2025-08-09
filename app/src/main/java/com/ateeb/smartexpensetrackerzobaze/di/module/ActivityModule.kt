package com.ateeb.smartexpensetrackerzobaze.di.module

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.ateeb.smartexpensetrackerzobaze.di.ActivityContext
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val activity: AppCompatActivity) {


    @ActivityContext
    @Provides
    fun provideContext(): Context = this.activity

}
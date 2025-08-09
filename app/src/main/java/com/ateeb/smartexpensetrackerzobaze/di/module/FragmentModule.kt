package com.ateeb.smartexpensetrackerzobaze.di.module

import android.content.Context
import androidx.fragment.app.Fragment
import dagger.Module
import dagger.Provides

@Module
class FragmentModule(private val fragment: Fragment) {

    @Provides
    fun provideContext(): Context = fragment.requireContext()

}
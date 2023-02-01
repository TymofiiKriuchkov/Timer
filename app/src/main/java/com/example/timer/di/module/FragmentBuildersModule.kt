package com.example.timer.di.module

import com.example.timer.ui.main.MainFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun initMainFragment(): MainFragment

}

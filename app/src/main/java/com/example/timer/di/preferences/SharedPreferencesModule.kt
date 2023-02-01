package com.example.timer.di.preferences

import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.example.timer.App
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Singleton


@Module
class SharedPreferencesModule @Inject constructor() {

    @Provides
    @Singleton
    @Inject
    internal fun provideSharedPreferences(app: App): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(app)
    }
}
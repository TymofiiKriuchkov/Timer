package com.example.timer.di

import com.example.timer.App
import com.example.timer.di.module.ActivityBuilderModule
import com.example.timer.di.module.AppModule
import com.example.timer.di.module.ContextModule
import com.example.timer.di.preferences.SharedPreferencesModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton


@Singleton
@Component(
        modules = [
            AppModule::class,
            ContextModule::class,
            AndroidInjectionModule::class,
            ActivityBuilderModule::class,
            SharedPreferencesModule::class
        ]
)

interface AppComponent {

    fun inject(app: App)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: App): Builder

        fun build(): AppComponent
    }
}
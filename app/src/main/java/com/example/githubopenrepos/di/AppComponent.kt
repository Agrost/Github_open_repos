package com.example.githubopenrepos.di

import android.content.Context
import com.example.githubopenrepos.di.home.HomeComponent
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(modules = [RetrofitModule::class, CacheModule::class])
@Singleton
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun registerHomeComponent(): HomeComponent.Factory
}
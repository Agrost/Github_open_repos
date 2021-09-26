package com.example.githubopenrepos.di.home

import com.example.githubopenrepos.di.scope.FragmentScope
import com.example.githubopenrepos.ui.fragments.HomeFragment
import dagger.Subcomponent

@Subcomponent(modules = [HomeViewModelModule::class])
@FragmentScope
interface HomeComponent {

    fun inject(homeFragment: HomeFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): HomeComponent
    }
}
package com.example.githubopenrepos.di.home

import androidx.lifecycle.ViewModel
import com.example.githubopenrepos.data.remote.HomeRemoteSource
import com.example.githubopenrepos.data.remote.HomeRemoteSourceImpl
import com.example.githubopenrepos.data.repository.HomeRepository
import com.example.githubopenrepos.data.repository.HomeRepositoryImpl
import com.example.githubopenrepos.di.keys.ViewModelKey
import com.example.githubopenrepos.di.scope.FragmentScope
import com.example.githubopenrepos.ui.viewmodels.HomeViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class HomeViewModelModule {

    @ViewModelKey(HomeViewModel::class)
    @FragmentScope
    @IntoMap
    @Binds
    abstract fun bindsHomeViewModule(
        homeViewModel: HomeViewModel
    ): ViewModel

    @Binds
    @FragmentScope
    abstract fun bindsPageRepository(
        homeRepositoryImpl: HomeRepositoryImpl
    ): HomeRepository

    @Binds
    @FragmentScope
    abstract fun bindsPageRemoteSource(
        homeRemoteSource: HomeRemoteSourceImpl
    ): HomeRemoteSource
}
package com.example.githubopenrepos.di

import com.example.githubopenrepos.data.cache.HomeCache
import com.example.githubopenrepos.data.cache.HomeCacheImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface CacheModule {
    @Binds
    @Singleton
    fun bindsCachePage(memoryCacheImpl: HomeCacheImpl): HomeCache
}
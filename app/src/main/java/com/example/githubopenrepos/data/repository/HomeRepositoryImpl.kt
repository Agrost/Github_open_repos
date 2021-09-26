package com.example.githubopenrepos.data.repository

import com.example.githubopenrepos.data.Answer
import com.example.githubopenrepos.data.cache.HomeCache
import com.example.githubopenrepos.data.remote.HomeRemoteSource
import com.example.githubopenrepos.data.toListGitCard
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val homeCache: HomeCache,
    private val homeRemoteSource: HomeRemoteSource
) : HomeRepository {

    override fun getData(): Single<Answer> {
        return homeCache.getHomeCache().flatMap {
            if (it is Answer.Failure) {
                getFirstPage()
            } else {
                Single.just(it)
            }
        }
    }

    override fun getFirstPage(): Single<Answer> {
        return homeRemoteSource.getJsonResponseDto(0)
            .map { homeCache.setGitCardList(it.toListGitCard()) }
            .flatMap { homeCache.getHomeCache() }
    }

    override fun getNextPage(): Single<Answer> {
        return homeRemoteSource.getJsonResponseDto(homeCache.getLastId())
            .map {
                val gitCardList = it.toListGitCard()
                homeCache.setNextPage(gitCardList)
                Answer.Success(gitCardList)
            }
    }
}


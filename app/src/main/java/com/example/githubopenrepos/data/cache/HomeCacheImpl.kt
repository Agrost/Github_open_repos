package com.example.githubopenrepos.data.cache

import com.example.githubopenrepos.data.Answer
import com.example.githubopenrepos.domain.entity.GitCard
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class HomeCacheImpl @Inject constructor() : HomeCache {

    private var gitCardList: List<GitCard>? = null
    private var lastId = 0

    override fun getLastId(): Int = lastId

    override fun getHomeCache(): Single<Answer> {
        if (gitCardList == null) {
            return Single.just(Answer.Failure())
        }
        return Single.just(Answer.Success(gitCardList!!))
    }

    override fun setGitCardList(gitCardList: List<GitCard>) {
        this.gitCardList = gitCardList
        lastId = gitCardList.last().id
    }

    override fun setNextPage(gitCardList: List<GitCard>) {
        val prevGitCardList = this.gitCardList?.toMutableList()
        prevGitCardList?.addAll(gitCardList)
        this.gitCardList = prevGitCardList
        lastId = gitCardList.last().id
    }
}
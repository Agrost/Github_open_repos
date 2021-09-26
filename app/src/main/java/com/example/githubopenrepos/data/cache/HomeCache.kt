package com.example.githubopenrepos.data.cache

import com.example.githubopenrepos.data.Answer
import com.example.githubopenrepos.domain.entity.GitCard
import io.reactivex.rxjava3.core.Single

interface HomeCache {
    fun getLastId(): Int
    fun getHomeCache(): Single<Answer>
    fun setGitCardList(gitCardList: List<GitCard>)
    fun setNextPage(gitCardList: List<GitCard>)
}
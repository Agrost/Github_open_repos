package com.example.githubopenrepos.data.repository

import com.example.githubopenrepos.data.Answer
import io.reactivex.rxjava3.core.Single

interface HomeRepository {
    fun getData(): Single<Answer>
    fun getFirstPage(): Single<Answer>
    fun getNextPage(): Single<Answer>
}
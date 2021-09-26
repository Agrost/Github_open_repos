package com.example.githubopenrepos.data

import com.example.githubopenrepos.data.remote.dto.JsonResponseDto
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("repositories")
    fun getJsonResponse(@Query("since") page: Int): Single<List<JsonResponseDto>>
}
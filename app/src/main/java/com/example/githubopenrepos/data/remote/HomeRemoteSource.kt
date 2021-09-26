package com.example.githubopenrepos.data.remote

import com.example.githubopenrepos.data.remote.dto.JsonResponseDto
import io.reactivex.rxjava3.core.Single

interface HomeRemoteSource {
    fun getJsonResponseDto(lastId: Int): Single<List<JsonResponseDto>>
}
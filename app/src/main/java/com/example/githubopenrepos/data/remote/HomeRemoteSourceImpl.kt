package com.example.githubopenrepos.data.remote

import com.example.githubopenrepos.data.Api
import com.example.githubopenrepos.data.remote.dto.JsonResponseDto
import com.example.githubopenrepos.data.toListGitCard
import com.example.githubopenrepos.domain.entity.GitCard
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class HomeRemoteSourceImpl @Inject constructor(
    private val api: Api
) : HomeRemoteSource {
    override fun getJsonResponseDto(lastId: Int): Single<List<JsonResponseDto>> {
        return api.getJsonResponse(lastId)
    }
}
package com.example.githubopenrepos.data

import com.example.githubopenrepos.data.remote.dto.JsonResponseDto
import com.example.githubopenrepos.domain.entity.GitCard


internal fun List<JsonResponseDto>.toListGitCard(): List<GitCard> {
    return map {
        GitCard(
            id = it.id,
            name = it.name,
            description = it.description,
            login = it.owner.login,
            url = it.url
        )
    }
}

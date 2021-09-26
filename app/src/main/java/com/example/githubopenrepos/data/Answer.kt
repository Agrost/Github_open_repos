package com.example.githubopenrepos.data

import com.example.githubopenrepos.domain.entity.GitCard

sealed class Answer {
    object Loading : Answer()
    class Success(
        val listGitCard: List<GitCard>
    ) : Answer()
    class Failure(
        val getNextPage: Boolean = false
    ) : Answer()
}
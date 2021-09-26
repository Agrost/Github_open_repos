package com.example.githubopenrepos.domain.entity

data class GitCard(
    val id: Int,
    val name: String,
    val description: String?,
    val login: String,
    val url: String,
)

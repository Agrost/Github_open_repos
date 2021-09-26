package com.example.githubopenrepos.data.remote.dto

import com.google.gson.annotations.SerializedName


data class JsonResponseDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("owner") val owner: Owner,
    @SerializedName("html_url") val url: String
)

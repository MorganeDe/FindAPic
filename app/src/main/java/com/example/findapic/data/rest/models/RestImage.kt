package com.example.findapic.data.rest.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RestImage(
    @SerialName("id")
    val id: Int,
    @SerialName("src")
    val source: RestSource,
    @SerialName("photographer")
    val photographer: String,
    @SerialName("alt")
    val description: String,
    @SerialName("url")
    val imagePageUrl: String,
)

@Serializable
data class RestSource(
    @SerialName("medium")
    val url: String,
)
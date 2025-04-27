package com.example.findapic.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RestImages(
    @SerialName("page")
    val currentPage: Int,
    @SerialName("photos")
    val images: List<RestImage>
)

package com.example.findapic.domain.models

data class ImageResource(
    val id: Int,
    val source: String,
    val description: String,
    val photographer: String,
    val imagePageUrl: String,
    val isFavorite: Boolean = false,
)
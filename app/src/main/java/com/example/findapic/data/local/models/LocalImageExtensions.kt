package com.example.findapic.data.local.models

import com.example.findapic.domain.models.ImageResource

fun LocalImage.toImageResource() = ImageResource(
    id = id,
    source = source,
    description = description,
    photographer = photographer,
    imagePageUrl = imagePageUrl,
    isFavorite = isFavorite,
)

fun List<LocalImage>.toImageResources() = map { it.toImageResource() }

fun ImageResource.toLocalImage() = LocalImage(
    id = id,
    source = source,
    description = description,
    photographer = photographer,
    imagePageUrl = imagePageUrl,
    isFavorite = isFavorite,
)
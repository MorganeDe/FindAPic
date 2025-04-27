package com.example.findapic.ui.commons.images

import com.example.findapic.domain.models.ImageResource

fun ImageResource.toUiImageItem() = UiImageItem(
    id = id,
    source = source,
    contentDescription = description,
    imagePageLink = imagePageUrl,
    photographer = photographer,
    isFavorite = isFavorite,
)

fun UiImageItem.toImageResource() = ImageResource(
    id = id,
    source = source,
    description = contentDescription,
    photographer = photographer,
    imagePageUrl = imagePageLink,
    isFavorite = isFavorite,
)

fun List<ImageResource>.toUiImageItems() = map { it.toUiImageItem() }
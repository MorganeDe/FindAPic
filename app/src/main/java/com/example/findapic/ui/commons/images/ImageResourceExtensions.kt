package com.example.findapic.ui.commons.images

import com.example.findapic.domain.models.ImageResource

fun ImageResource.toUiImageItem() = UiImageItem(
    id,
    source,
    description,
    imagePageUrl,
    photographer,
)

fun List<ImageResource>.toUiImageItems() = map { it.toUiImageItem() }
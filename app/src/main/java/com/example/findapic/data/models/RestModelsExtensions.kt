package com.example.findapic.data.models

import com.example.findapic.domain.models.ImageResource

fun RestImage.toImageResource() = ImageResource(
    id = id,
    source = source.url,
    description = description,
    photographer = photographer,
    imagePageUrl = imagePageUrl,
)
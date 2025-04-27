package com.example.findapic.domain.usecases

import com.example.findapic.domain.models.ImageResource

interface ToggleFavoriteImageUseCase {
    suspend fun toggleFavoriteImage(imageResource: ImageResource)
}
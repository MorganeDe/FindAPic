package com.example.findapic.domain.usecases

import com.example.findapic.domain.models.ImageResource
import com.example.findapic.domain.repositories.ImagesRepository

class ToggleFavoriteImageUseCaseImpl(private val imagesRepository: ImagesRepository) :
    ToggleFavoriteImageUseCase {
    override suspend fun toggleFavoriteImage(imageResource: ImageResource) {
        imagesRepository.toggleFavoriteImage(imageResource)
    }
}
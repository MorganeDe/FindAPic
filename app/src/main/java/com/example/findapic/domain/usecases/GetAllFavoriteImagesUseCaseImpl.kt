package com.example.findapic.domain.usecases

import com.example.findapic.domain.models.ImageResource
import com.example.findapic.domain.repositories.ImagesRepository
import kotlinx.coroutines.flow.Flow

class GetAllFavoriteImagesUseCaseImpl(private val imagesRepository: ImagesRepository) :
    GetAllFavoriteImagesUseCase {
    override suspend fun getAllFavoriteImages(): Flow<Result<List<ImageResource>>> {
        return imagesRepository.getAllFavoriteImages()
    }
}
package com.example.findapic.domain.usecases

import com.example.findapic.domain.models.ImageResource
import com.example.findapic.domain.repositories.ImagesRepository
import kotlinx.coroutines.flow.Flow

class SearchImagesUseCaseImpl(private val imagesRepository: ImagesRepository) :
    SearchImagesUseCase {
    override suspend fun searchImages(query: String): Flow<Result<List<ImageResource>>> {
        return imagesRepository.searchImages(query)
    }
}
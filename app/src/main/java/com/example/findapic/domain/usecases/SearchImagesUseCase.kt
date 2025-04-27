package com.example.findapic.domain.usecases

import com.example.findapic.domain.models.ImageResource
import kotlinx.coroutines.flow.Flow

interface SearchImagesUseCase {
    suspend fun searchImages(query: String): Flow<Result<List<ImageResource>>>
}
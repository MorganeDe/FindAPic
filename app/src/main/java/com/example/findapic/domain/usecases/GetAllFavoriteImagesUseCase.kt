package com.example.findapic.domain.usecases

import com.example.findapic.domain.models.ImageResource
import kotlinx.coroutines.flow.Flow

interface GetAllFavoriteImagesUseCase {
    suspend fun getAllFavoriteImages(): Flow<Result<List<ImageResource>>>
}
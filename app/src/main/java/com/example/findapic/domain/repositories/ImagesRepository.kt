package com.example.findapic.domain.repositories

import com.example.findapic.domain.models.ImageResource
import kotlinx.coroutines.flow.Flow

interface ImagesRepository {
    suspend fun searchImages(query: String): Flow<Result<List<ImageResource>>>
    suspend fun getAllFavoriteImages(): Flow<Result<List<ImageResource>>>
    suspend fun toggleFavoriteImage(imageResource: ImageResource)
}
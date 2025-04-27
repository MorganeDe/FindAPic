package com.example.findapic.data.services

import com.example.findapic.commons_io.wrapToResult
import com.example.findapic.data.apis.PexelsApi
import com.example.findapic.data.models.toImageResource
import com.example.findapic.domain.models.ImageResource
import com.example.findapic.domain.repositories.ImagesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class ImagesRepositoryImpl(private val pexelsApi: PexelsApi) : ImagesRepository {
    override suspend fun searchImages(query: String): Flow<Result<List<ImageResource>>> =
        withContext(Dispatchers.IO) {
            flow {
                emit(
                    wrapToResult { pexelsApi.searchImages(query) }.map {
                        it.images.map { image -> image.toImageResource() }
                    }
                )
            }
        }
}
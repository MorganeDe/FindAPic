package com.example.findapic.data.services

import com.example.findapic.commons_io.wrapToResult
import com.example.findapic.data.local.apis.ImageDao
import com.example.findapic.data.local.models.toImageResource
import com.example.findapic.data.local.models.toImageResources
import com.example.findapic.data.local.models.toLocalImage
import com.example.findapic.data.rest.apis.PexelsApi
import com.example.findapic.data.rest.models.toImageResource
import com.example.findapic.domain.models.ImageResource
import com.example.findapic.domain.repositories.ImagesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import retrofit2.Response

class ImagesRepositoryImpl(private val pexelsApi: PexelsApi, private val imageDao: ImageDao) :
    ImagesRepository {
    override suspend fun searchImages(query: String): Flow<Result<List<ImageResource>>> =
        withContext(Dispatchers.IO) {
            flow {
                emit(
                    wrapToResult { pexelsApi.searchImages(query) }.map {
                        it.images.map { restImage -> restImage.toImageResource() }
                            .let { imageResources -> getImagesWithFavorites(imageResources) }
                    }
                )
            }
        }

    override suspend fun getAllFavoriteImages(): Flow<Result<List<ImageResource>>> =
        withContext(Dispatchers.IO) {
            flow {
                emit(
                    wrapToResult { Response.success(imageDao.getAllFavoriteLocalImages()) }.map {
                        it.map { localImage -> localImage.toImageResource() }
                    }
                )
            }
        }

    override suspend fun toggleFavoriteImage(imageResource: ImageResource) =
        withContext(Dispatchers.IO) {
            imageDao.getLocalImageByIdOrNull(imageResource.id)?.let {
                imageDao.deleteLocalImageById(imageResource.id)
            } ?: imageDao.upsertLocalImage(
                imageResource.copy(isFavorite = true).toLocalImage()
            )
        }

    private suspend fun getImagesWithFavorites(images: List<ImageResource>) =
        imageDao.getAllFavoriteLocalImages().toImageResources().let { favoriteImages ->
            images.map { image -> favoriteImages.find { it.id == image.id } ?: image }
        }
}
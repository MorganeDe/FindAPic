package com.example.findapic.domain.usecases

import com.example.findapic.domain.models.ImageResource
import com.example.findapic.domain.repositories.ImagesRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetAllFavoriteImagesTest {
    private val imagesRepository = mockk<ImagesRepository>()
    private lateinit var getAllFavoriteImagesUseCase: GetAllFavoriteImagesUseCase

    @Before
    fun setup() {
        getAllFavoriteImagesUseCase = GetAllFavoriteImagesUseCaseImpl(imagesRepository)
    }

    @Test
    fun `getAllFavoriteImages should return successful result of image resources`() = runTest {
        val imageResources = listOf(
            ImageResource(
                id = 0,
                source = "https://images.pexels.com/photos/2014422/pexels-photo-2014422.jpeg?auto=compress&cs=tinysrgb&h=130",
                description = "Brown Rocks During Golden Hour",
                imagePageUrl = "https://www.pexels.com/photo/brown-rocks-during-golden-hour-2014422/",
                photographer = "Joey Farina",
                isFavorite = true,
            ),
        )

        coEvery { imagesRepository.getAllFavoriteImages() } returns flowOf(
            Result.success(imageResources)
        )

        getAllFavoriteImagesUseCase.getAllFavoriteImages().collect {
            assertThat(it).isEqualTo(Result.success(imageResources))
        }
    }
}
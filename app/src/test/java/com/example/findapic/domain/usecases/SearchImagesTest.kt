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

class SearchImagesTest {
    private val imagesRepository = mockk<ImagesRepository>()
    private lateinit var searchImagesUseCase: SearchImagesUseCase

    @Before
    fun setup() {
        searchImagesUseCase = SearchImagesUseCaseImpl(imagesRepository)
    }

    @Test
    fun `searchImages should return successful result of image resources`() = runTest {
        val imageResources = listOf(
            ImageResource(
                id = 0,
                source = "https://images.pexels.com/photos/2014422/pexels-photo-2014422.jpeg?auto=compress&cs=tinysrgb&h=130",
                description = "Brown Rocks During Golden Hour",
                imagePageUrl = "https://www.pexels.com/photo/brown-rocks-during-golden-hour-2014422/",
                photographer = "Joey Farina",
                isFavorite = false
            ),
            ImageResource(
                id = 1,
                source = "source",
                description = "description",
                photographer = "photographer",
                imagePageUrl = "image page link",
                isFavorite = true,
            ),
        )

        coEvery { imagesRepository.searchImages(any()) } returns flowOf(
            Result.success(imageResources)
        )

        searchImagesUseCase.searchImages("").collect {
            assertThat(it).isEqualTo(Result.success(imageResources))
        }
    }
}
package com.example.findapic.domain.usecases

import com.example.findapic.domain.models.ImageResource
import com.example.findapic.domain.repositories.ImagesRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class ToggleFavoriteImageTest {
    private val imagesRepository = mockk<ImagesRepository>()
    private lateinit var toggleFavoriteImageUseCase: ToggleFavoriteImageUseCase

    @Before
    fun setup() {
        toggleFavoriteImageUseCase = ToggleFavoriteImageUseCaseImpl(imagesRepository)
    }

    @Test
    fun `toggleFavoriteImage should toggle image with corresponding image`() = runTest {
        val imageResource = ImageResource(
            id = 0,
            source = "https://images.pexels.com/photos/2014422/pexels-photo-2014422.jpeg?auto=compress&cs=tinysrgb&h=130",
            description = "Brown Rocks During Golden Hour",
            imagePageUrl = "https://www.pexels.com/photo/brown-rocks-during-golden-hour-2014422/",
            photographer = "Joey Farina",
            isFavorite = true,
        )

        val imageResourceSlot = slot<ImageResource>()
        coEvery { imagesRepository.toggleFavoriteImage(capture(imageResourceSlot)) } returns Unit

        toggleFavoriteImageUseCase.toggleFavoriteImage(imageResource)

        assertThat(imageResourceSlot.captured).isEqualTo(imageResource)
    }
}
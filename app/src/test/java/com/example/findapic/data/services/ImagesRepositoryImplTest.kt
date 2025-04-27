package com.example.findapic.data.services

import com.example.findapic.data.apis.PexelsApi
import com.example.findapic.data.models.RestImage
import com.example.findapic.data.models.RestImages
import com.example.findapic.data.models.RestSource
import com.example.findapic.domain.models.ImageResource
import com.example.findapic.domain.repositories.ImagesRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import java.net.UnknownHostException

class ImagesRepositoryImplTest {
    private val pexelsApi = mockk<PexelsApi>()
    private lateinit var imagesRepository: ImagesRepository

    @Before
    fun setup() {
        imagesRepository = ImagesRepositoryImpl(pexelsApi)
    }

    @Test
    fun `searchImages should return result of image resources when fetch is successful`() =
        runTest {
            val restImages = RestImages(
                currentPage = 1,
                images = listOf(
                    RestImage(
                        id = 0,
                        source = RestSource(
                            url = "https://images.pexels.com/photos/2014422/pexels-photo-2014422.jpeg?auto=compress&cs=tinysrgb&h=130"
                        ),
                        description = "Brown Rocks During Golden Hour",
                        imagePageUrl = "https://www.pexels.com/photo/brown-rocks-during-golden-hour-2014422/",
                        photographer = "Joey Farina",
                    ),
                ),
            )

            coEvery { pexelsApi.searchImages(any()) } returns Response.success(restImages)

            imagesRepository.searchImages("").collect {
                assertThat(it).isEqualTo(
                    Result.success(
                        listOf(
                            ImageResource(
                                id = 0,
                                source = "https://images.pexels.com/photos/2014422/pexels-photo-2014422.jpeg?auto=compress&cs=tinysrgb&h=130",
                                description = "Brown Rocks During Golden Hour",
                                imagePageUrl = "https://www.pexels.com/photo/brown-rocks-during-golden-hour-2014422/",
                                photographer = "Joey Farina",
                            ),
                        )
                    )
                )
            }
        }

    @Test
    fun `searchImages should return result failure when fetch fails`() = runTest {
        coEvery { pexelsApi.searchImages(any()) } throws UnknownHostException("Unknown host")

        imagesRepository.searchImages("").catch {
            assertThat(it).isInstanceOf(UnknownHostException::class.java)
        }.collect { }
    }
}
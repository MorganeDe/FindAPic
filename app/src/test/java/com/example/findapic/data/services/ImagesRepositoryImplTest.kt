package com.example.findapic.data.services

import com.example.findapic.data.local.apis.ImageDao
import com.example.findapic.data.local.models.LocalImage
import com.example.findapic.data.rest.apis.PexelsApi
import com.example.findapic.data.rest.models.RestImage
import com.example.findapic.data.rest.models.RestImages
import com.example.findapic.data.rest.models.RestSource
import com.example.findapic.domain.models.ImageResource
import com.example.findapic.domain.repositories.ImagesRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import java.net.UnknownHostException

class ImagesRepositoryImplTest {
    private val pexelsApi = mockk<PexelsApi>()
    private val imageDao = mockk<ImageDao>()
    private lateinit var imagesRepository: ImagesRepository

    @Before
    fun setup() {
        imagesRepository = ImagesRepositoryImpl(pexelsApi, imageDao)
    }

    @Test
    fun `searchImages should return result of image resources when fetch is successful and no favorite images are found`() =
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
            coEvery { imageDao.getAllFavoriteLocalImages() } returns listOf()

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
                                isFavorite = false,
                            ),
                        )
                    )
                )
            }
        }

    @Test
    fun `searchImages should return result of image resources with favorites when fetch is successful and favorite images are found`() =
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
                    RestImage(
                        id = 1,
                        source = RestSource(
                            url = "https://images.pexels.com/photos/2014422/pexels-photo-2014422.jpeg?auto=compress&cs=tinysrgb&h=130"
                        ),
                        description = "Australian Shepherd and Samoyed Playing on Beach",
                        imagePageUrl = "https://www.pexels.com/photo/australian-shepherd-and-samoyed-playing-on-beach-31009027/",
                        photographer = "Elina Volkova",
                    ),
                ),
            )

            coEvery { pexelsApi.searchImages(any()) } returns Response.success(restImages)
            coEvery { imageDao.getAllFavoriteLocalImages() } returns listOf(
                LocalImage(
                    id = 0,
                    source = "https://images.pexels.com/photos/2014422/pexels-photo-2014422.jpeg?auto=compress&cs=tinysrgb&h=130",
                    description = "Brown Rocks During Golden Hour",
                    imagePageUrl = "https://www.pexels.com/photo/brown-rocks-during-golden-hour-2014422/",
                    photographer = "Joey Farina",
                    isFavorite = true,
                ),
            )

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
                                isFavorite = true,
                            ),
                            ImageResource(
                                id = 1,
                                source = "https://images.pexels.com/photos/2014422/pexels-photo-2014422.jpeg?auto=compress&cs=tinysrgb&h=130",
                                description = "Australian Shepherd and Samoyed Playing on Beach",
                                imagePageUrl = "https://www.pexels.com/photo/australian-shepherd-and-samoyed-playing-on-beach-31009027/",
                                photographer = "Elina Volkova",
                                isFavorite = false,
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

    @Test
    fun `getAllFavoriteImages should return successful result of favorite local images`() =
        runTest {
            coEvery { imageDao.getAllFavoriteLocalImages() } returns listOf(
                LocalImage(
                    id = 0,
                    source = "https://images.pexels.com/photos/2014422/pexels-photo-2014422.jpeg?auto=compress&cs=tinysrgb&h=130",
                    description = "Brown Rocks During Golden Hour",
                    imagePageUrl = "https://www.pexels.com/photo/brown-rocks-during-golden-hour-2014422/",
                    photographer = "Joey Farina",
                    isFavorite = true,
                ),
            )

            imagesRepository.getAllFavoriteImages().collect {
                assertThat(it).isEqualTo(
                    Result.success(
                        listOf(
                            ImageResource(
                                id = 0,
                                source = "https://images.pexels.com/photos/2014422/pexels-photo-2014422.jpeg?auto=compress&cs=tinysrgb&h=130",
                                description = "Brown Rocks During Golden Hour",
                                imagePageUrl = "https://www.pexels.com/photo/brown-rocks-during-golden-hour-2014422/",
                                photographer = "Joey Farina",
                                isFavorite = true,
                            ),
                        )
                    )
                )
            }
        }

    @Test
    fun `toggleFavoriteImage should add image to favorites when it is not in favorites yet`() =
        runTest {
            val imageResource = ImageResource(
                id = 0,
                source = "https://images.pexels.com/photos/2014422/pexels-photo-2014422.jpeg?auto=compress&cs=tinysrgb&h=130",
                description = "Brown Rocks During Golden Hour",
                imagePageUrl = "https://www.pexels.com/photo/brown-rocks-during-golden-hour-2014422/",
                photographer = "Joey Farina",
                isFavorite = false,
            )

            val localImageSlot = slot<LocalImage>()

            coEvery { imageDao.getLocalImageByIdOrNull(any()) } returns null
            coEvery { imageDao.upsertLocalImage(capture(localImageSlot)) } returns Unit

            imagesRepository.toggleFavoriteImage(imageResource)

            assertThat(localImageSlot.captured).isEqualTo(
                LocalImage(
                    id = 0,
                    source = "https://images.pexels.com/photos/2014422/pexels-photo-2014422.jpeg?auto=compress&cs=tinysrgb&h=130",
                    description = "Brown Rocks During Golden Hour",
                    imagePageUrl = "https://www.pexels.com/photo/brown-rocks-during-golden-hour-2014422/",
                    photographer = "Joey Farina",
                    isFavorite = true,
                )
            )
        }

    @Test
    fun `toggleFavoriteImage should remove image from favorites when it is already in favorites`() =
        runTest {
            val imageResource = ImageResource(
                id = 0,
                source = "https://images.pexels.com/photos/2014422/pexels-photo-2014422.jpeg?auto=compress&cs=tinysrgb&h=130",
                description = "Brown Rocks During Golden Hour",
                imagePageUrl = "https://www.pexels.com/photo/brown-rocks-during-golden-hour-2014422/",
                photographer = "Joey Farina",
                isFavorite = true,
            )

            val localImageIdSlot = slot<Int>()

            coEvery { imageDao.getLocalImageByIdOrNull(any()) } returns LocalImage(
                id = 0,
                source = "https://images.pexels.com/photos/2014422/pexels-photo-2014422.jpeg?auto=compress&cs=tinysrgb&h=130",
                description = "Brown Rocks During Golden Hour",
                imagePageUrl = "https://www.pexels.com/photo/brown-rocks-during-golden-hour-2014422/",
                photographer = "Joey Farina",
                isFavorite = true,
            )
            coEvery { imageDao.deleteLocalImageById(capture(localImageIdSlot)) } returns Unit

            imagesRepository.toggleFavoriteImage(imageResource)

            assertThat(localImageIdSlot.captured).isEqualTo(0)
        }
}
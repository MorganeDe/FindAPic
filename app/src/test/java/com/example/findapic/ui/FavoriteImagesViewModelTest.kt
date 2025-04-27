@file:OptIn(ExperimentalCoroutinesApi::class)

package com.example.findapic.ui

import com.example.findapic.domain.models.ImageResource
import com.example.findapic.domain.usecases.GetAllFavoriteImagesUseCase
import com.example.findapic.domain.usecases.ToggleFavoriteImageUseCase
import com.example.findapic.helpers.MainDispatcherRule
import com.example.findapic.ui.commons.images.UiImageItem
import com.example.findapic.ui.favorites.FavoriteImagesViewModel
import com.example.findapic.ui.favorites.FavoriteImagesViewState
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class FavoriteImagesViewModelTest {
    private lateinit var favoriteImagesViewModel: FavoriteImagesViewModel

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val getAllFavoriteImagesUseCase = mockk<GetAllFavoriteImagesUseCase>()
    private val toggleFavoriteImageUseCase = mockk<ToggleFavoriteImageUseCase>()

    @Before
    fun setup() {
        favoriteImagesViewModel =
            FavoriteImagesViewModel(getAllFavoriteImagesUseCase, toggleFavoriteImageUseCase)
    }

    @Test
    fun `init should update view state with result of favorite images when there are favorites`() =
        runTest {
            coEvery { getAllFavoriteImagesUseCase.getAllFavoriteImages() } returns flowOf(
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

            favoriteImagesViewModel.init()

            val viewStates = mutableListOf<FavoriteImagesViewState>()
            backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
                favoriteImagesViewModel.viewState.toList(viewStates)
            }

            assertThat(viewStates.last()).isEqualTo(
                FavoriteImagesViewState.FavoriteImagesSuccess(
                    listOf(
                        UiImageItem(
                            id = 0,
                            source = "https://images.pexels.com/photos/2014422/pexels-photo-2014422.jpeg?auto=compress&cs=tinysrgb&h=130",
                            contentDescription = "Brown Rocks During Golden Hour",
                            imagePageLink = "https://www.pexels.com/photo/brown-rocks-during-golden-hour-2014422/",
                            photographer = "Joey Farina",
                        ),
                    )
                )
            )
        }

    @Test
    fun `init should update view state with empty result when there are no favorite images`() =
        runTest {
            coEvery { getAllFavoriteImagesUseCase.getAllFavoriteImages() } returns flowOf(
                Result.success(listOf())
            )

            favoriteImagesViewModel.init()

            val viewStates = mutableListOf<FavoriteImagesViewState>()
            backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
                favoriteImagesViewModel.viewState.toList(viewStates)
            }

            assertThat(viewStates.last()).isEqualTo(FavoriteImagesViewState.NoFavorites)
        }

    @Test
    fun `init should update view state with error result when fetch fails`() =
        runTest {
            coEvery { getAllFavoriteImagesUseCase.getAllFavoriteImages() } returns flow {
                throw Exception("Unable to fetch favorites")
            }

            favoriteImagesViewModel.init()

            val viewStates = mutableListOf<FavoriteImagesViewState>()
            backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
                favoriteImagesViewModel.viewState.toList(viewStates)
            }

            assertThat(viewStates.last()).isEqualTo(
                FavoriteImagesViewState.Error("Unable to fetch favorites")
            )
        }

    @Test
    fun `removeFromFavorites should update view state with favorites result without removed favorite`() =
        runTest {
            val imageToRemoveFromFavorites = UiImageItem(
                id = 1,
                source = "https://images.pexels.com/photos/2014422/pexels-photo-2014422.jpeg?auto=compress&cs=tinysrgb&h=130",
                contentDescription = "Australian Shepherd and Samoyed Playing on Beach",
                imagePageLink = "https://www.pexels.com/photo/australian-shepherd-and-samoyed-playing-on-beach-31009027/",
                photographer = "Elina Volkova",
                isFavorite = true,
            )
            coEvery { toggleFavoriteImageUseCase.toggleFavoriteImage(any()) } returns Unit
            coEvery { getAllFavoriteImagesUseCase.getAllFavoriteImages() } returns flowOf(
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

            favoriteImagesViewModel.removeFromFavorites(imageToRemoveFromFavorites)

            val viewStates = mutableListOf<FavoriteImagesViewState>()
            backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
                favoriteImagesViewModel.viewState.toList(viewStates)
            }

            assertThat(viewStates.last()).isEqualTo(
                FavoriteImagesViewState.FavoriteImagesSuccess(
                    listOf(
                        UiImageItem(
                            id = 0,
                            source = "https://images.pexels.com/photos/2014422/pexels-photo-2014422.jpeg?auto=compress&cs=tinysrgb&h=130",
                            contentDescription = "Brown Rocks During Golden Hour",
                            imagePageLink = "https://www.pexels.com/photo/brown-rocks-during-golden-hour-2014422/",
                            photographer = "Joey Farina",
                            isFavorite = true,
                        ),
                    )
                )
            )
        }
}
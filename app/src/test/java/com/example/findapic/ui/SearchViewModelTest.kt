@file:OptIn(ExperimentalCoroutinesApi::class)

package com.example.findapic.ui

import com.example.findapic.domain.models.FindAPicError
import com.example.findapic.domain.models.ImageResource
import com.example.findapic.domain.usecases.SearchImagesUseCase
import com.example.findapic.helpers.MainDispatcherRule
import com.example.findapic.ui.commons.images.UiImageItem
import com.example.findapic.ui.search.SearchImagesViewModel
import com.example.findapic.ui.search.SearchImagesViewState
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SearchViewModelTest {
    private lateinit var searchImagesViewModel: SearchImagesViewModel

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val searchImagesUseCase = mockk<SearchImagesUseCase>()

    @Before
    fun setup() {
        searchImagesViewModel = SearchImagesViewModel(searchImagesUseCase)
    }

    @Test
    fun `init should update view state with idle state when query is empty`() = runTest {
        searchImagesViewModel.init()

        val viewStates = mutableListOf<SearchImagesViewState>()
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            searchImagesViewModel.viewState.toList(viewStates)
        }

        assertThat(viewStates.last()).isEqualTo(SearchImagesViewState.Idle)
    }

    @Test
    fun `init should update view state with result of images when search is successful`() =
        runTest {
            coEvery { searchImagesUseCase.searchImages(any()) } returns flowOf(
                Result.success(
                    listOf(
                        ImageResource(
                            id = 0,
                            source = "https://images.pexels.com/photos/2014422/pexels-photo-2014422.jpeg?auto=compress&cs=tinysrgb&h=130",
                            description = "Brown Rocks During Golden Hour",
                            imagePageUrl = "https://www.pexels.com/photo/brown-rocks-during-golden-hour-2014422/",
                            photographer = "Joey Farina",
                        ),
                        ImageResource(
                            id = 1,
                            source = "source",
                            description = "description",
                            photographer = "photographer",
                            imagePageUrl = "image page link",
                        ),
                    )
                )
            )

            searchImagesViewModel.onQueryChange("new query")
            searchImagesViewModel.init()

            advanceTimeBy(500L)

            val viewStates = mutableListOf<SearchImagesViewState>()
            backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
                searchImagesViewModel.viewState.toList(viewStates)
            }

            advanceUntilIdle()

            assertThat(viewStates.last()).isEqualTo(
                SearchImagesViewState.ImageResultsSuccess(
                    listOf(
                        UiImageItem(
                            id = 0,
                            source = "https://images.pexels.com/photos/2014422/pexels-photo-2014422.jpeg?auto=compress&cs=tinysrgb&h=130",
                            contentDescription = "Brown Rocks During Golden Hour",
                            imagePageLink = "https://www.pexels.com/photo/brown-rocks-during-golden-hour-2014422/",
                            photographer = "Joey Farina",
                        ),
                        UiImageItem(
                            id = 1,
                            source = "source",
                            contentDescription = "description",
                            photographer = "photographer",
                            imagePageLink = "image page link",
                        ),
                    )
                )
            )
        }

    @Test
    fun `init should update view state with empty result when no search results are found`() =
        runTest {
            coEvery { searchImagesUseCase.searchImages(any()) } returns flowOf(
                Result.success(listOf())
            )

            searchImagesViewModel.onQueryChange("new query")
            searchImagesViewModel.init()

            advanceTimeBy(500L)

            val viewStates = mutableListOf<SearchImagesViewState>()
            backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
                searchImagesViewModel.viewState.toList(viewStates)
            }

            advanceUntilIdle()

            assertThat(viewStates.last()).isEqualTo(SearchImagesViewState.NoResults)
        }

    @Test
    fun `init should update view state with error when search has failed`() = runTest {
        coEvery { searchImagesUseCase.searchImages(any()) } returns flow {
            throw FindAPicError.ServerError("Server error")
        }

        searchImagesViewModel.onQueryChange("query with error")
        searchImagesViewModel.init()
        advanceTimeBy(500L)

        val viewStates = mutableListOf<SearchImagesViewState>()
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            searchImagesViewModel.viewState.toList(viewStates)
        }

        advanceUntilIdle()

        assertThat(viewStates.last()).isEqualTo(
            SearchImagesViewState.Error("Server error")
        )
    }

    @Test
    fun `onQueryChange should update query with new one`() = runTest {
        searchImagesViewModel.onQueryChange("new query")

        val queryStates = mutableListOf<String>()
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            searchImagesViewModel.query.toList(queryStates)
        }

        assertThat(queryStates.last()).isEqualTo("new query")
    }
}
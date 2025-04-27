package com.example.findapic.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.findapic.commons_io.toFindAPicError
import com.example.findapic.domain.models.ImageResource
import com.example.findapic.domain.usecases.SearchImagesUseCase
import com.example.findapic.ui.commons.images.UiImageItem
import com.example.findapic.ui.commons.images.toUiImageItems
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchImagesViewModel(private val searchImagesUseCase: SearchImagesUseCase) : ViewModel() {
    val viewState: StateFlow<SearchImagesViewState>
        get() = _viewState
    private val _viewState: MutableStateFlow<SearchImagesViewState> =
        MutableStateFlow(SearchImagesViewState.Idle)

    val query: StateFlow<String>
        get() = _query
    private val _query = MutableStateFlow(EMPTY_QUERY)

    @OptIn(FlowPreview::class)
    fun init() {
        viewModelScope.launch {
            query.debounce(DEBOUNCE_TIMEOUT_IN_MILLISECONDS).collectLatest { latestQuery ->
                if (latestQuery.isEmpty()) {
                    _viewState.update { SearchImagesViewState.Idle }
                    return@collectLatest
                }

                _viewState.update { SearchImagesViewState.Loading }

                searchForQuery(latestQuery)
            }
        }
    }

    fun onQueryChange(newQuery: String) {
        _query.update { newQuery }
    }

    private suspend fun searchForQuery(query: String) {
        searchImagesUseCase.searchImages(query)
            .catch { emit(Result.failure(it.toFindAPicError())) }
            .collect {
                it.onSuccess(::onSuccessfulResult)
                    .onFailure(::onErrorResult)
            }
    }

    private fun onSuccessfulResult(images: List<ImageResource>) = if (images.isEmpty()) {
        _viewState.update { SearchImagesViewState.NoResults }
    } else {
        _viewState.update { SearchImagesViewState.ImageResultsSuccess(images.toUiImageItems()) }
    }

    private fun onErrorResult(error: Throwable) {
        _viewState.update {
            SearchImagesViewState.Error(error.toFindAPicError().message)
        }
    }

    companion object {
        private const val EMPTY_QUERY = ""
        private const val DEBOUNCE_TIMEOUT_IN_MILLISECONDS = 500L
    }
}

sealed class SearchImagesViewState {
    data object Idle : SearchImagesViewState()
    data object Loading : SearchImagesViewState()
    data class ImageResultsSuccess(val imageResults: List<UiImageItem>) : SearchImagesViewState()
    data object NoResults : SearchImagesViewState()
    data class Error(val message: String) : SearchImagesViewState()
}
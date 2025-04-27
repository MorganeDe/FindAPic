package com.example.findapic.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.findapic.commons_io.toFindAPicError
import com.example.findapic.domain.models.ImageResource
import com.example.findapic.domain.usecases.GetAllFavoriteImagesUseCase
import com.example.findapic.domain.usecases.ToggleFavoriteImageUseCase
import com.example.findapic.ui.commons.images.UiImageItem
import com.example.findapic.ui.commons.images.toImageResource
import com.example.findapic.ui.commons.images.toUiImageItems
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FavoriteImagesViewModel(
    private val getAllFavoriteImagesUseCase: GetAllFavoriteImagesUseCase,
    private val toggleFavoriteImageUseCase: ToggleFavoriteImageUseCase,
) : ViewModel() {
    val viewState: StateFlow<FavoriteImagesViewState>
        get() = _viewState
    private val _viewState =
        MutableStateFlow<FavoriteImagesViewState>(FavoriteImagesViewState.Loading)

    fun init() {
        viewModelScope.launch {
            getAllFavoriteImages()
        }
    }

    fun removeFromFavorites(uiImageItem: UiImageItem) {
        viewModelScope.launch {
            toggleFavoriteImageUseCase.toggleFavoriteImage(uiImageItem.toImageResource())
            getAllFavoriteImages()
        }
    }

    private suspend fun getAllFavoriteImages() {
        getAllFavoriteImagesUseCase.getAllFavoriteImages()
            .catch { emit(Result.failure(it.toFindAPicError())) }
            .collect {
                it.onSuccess(::onFavoritesSuccess)
                    .onFailure(::onFavoritesError)
            }
    }

    private fun onFavoritesSuccess(images: List<ImageResource>) = if (images.isEmpty()) {
        _viewState.update { FavoriteImagesViewState.NoFavorites }
    } else {
        _viewState.update { FavoriteImagesViewState.FavoriteImagesSuccess(images.toUiImageItems()) }
    }

    private fun onFavoritesError(error: Throwable) {
        _viewState.update {
            FavoriteImagesViewState.Error(error.toFindAPicError().message)
        }
    }
}

sealed class FavoriteImagesViewState {
    data object Loading : FavoriteImagesViewState()
    data class FavoriteImagesSuccess(val favoriteImages: List<UiImageItem>) :
        FavoriteImagesViewState()

    data object NoFavorites : FavoriteImagesViewState()
    data class Error(val message: String) : FavoriteImagesViewState()
}
package com.example.findapic.ui.favorites

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.LifecycleStartEffect
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.findapic.R
import com.example.findapic.ui.commons.header.Header
import com.example.findapic.ui.commons.images.ImageList
import org.koin.androidx.compose.koinViewModel

@Composable
fun FavoriteImagesScreen(viewModel: FavoriteImagesViewModel = koinViewModel()) {
    val viewState by viewModel.viewState.collectAsStateWithLifecycle()

    Column {
        Header(title = stringResource(R.string.favorite_images_screen_title))
        Box(modifier = Modifier.fillMaxSize()) {
            when (val currentViewState = viewState) {
                is FavoriteImagesViewState.Error -> {
                    Text(
                        text = currentViewState.message,
                        modifier = Modifier.align(Alignment.Center),
                    )
                }

                is FavoriteImagesViewState.FavoriteImagesSuccess -> {
                    ImageList(currentViewState.favoriteImages) { viewModel.removeFromFavorites(it) }
                }

                FavoriteImagesViewState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

                FavoriteImagesViewState.NoFavorites -> {
                    Text(
                        text = stringResource(R.string.favorite_images_screen_no_favorites),
                        modifier = Modifier.align(Alignment.Center),
                    )
                }
            }
        }
    }

    LifecycleStartEffect(key1 = viewModel, lifecycleOwner = LocalLifecycleOwner.current) {
        viewModel.init()
        onStopOrDispose { }
    }
}
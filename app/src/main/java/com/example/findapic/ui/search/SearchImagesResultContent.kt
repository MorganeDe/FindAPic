package com.example.findapic.ui.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.findapic.R
import com.example.findapic.ui.commons.images.ImageList
import com.example.findapic.ui.commons.images.UiImageItem

@Composable
fun SearchImagesResultContent(
    query: String,
    currentViewState: SearchImagesViewState,
    onFavoriteButtonClick: (UiImageItem) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        when (currentViewState) {
            is SearchImagesViewState.Error -> {
                Text(
                    text = currentViewState.message,
                    modifier = Modifier.align(Alignment.Center),
                )
            }

            is SearchImagesViewState.ImageResultsSuccess -> {
                ImageList(currentViewState.imageResults, onFavoriteButtonClick)
            }

            SearchImagesViewState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            SearchImagesViewState.NoResults -> {
                Text(
                    text = stringResource(R.string.search_screen_no_results, query),
                    modifier = Modifier.align(Alignment.Center),
                )
            }

            SearchImagesViewState.Idle -> {
                Text(
                    text = stringResource(R.string.search_screen_empty_query),
                    modifier = Modifier.align(Alignment.Center),
                )
            }
        }
    }
}
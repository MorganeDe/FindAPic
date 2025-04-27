package com.example.findapic.ui.search

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.LifecycleStartEffect
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.findapic.R
import com.example.findapic.ui.commons.header.Header
import com.example.findapic.ui.theme.FindAPicTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun SearchImagesScreen(viewModel: SearchImagesViewModel = koinViewModel()) {
    val viewState by viewModel.viewState.collectAsStateWithLifecycle()
    val query by viewModel.query.collectAsStateWithLifecycle()

    Column {
        Header(stringResource(R.string.search_screen_title))
        ImagesSearchBar(
            query = query,
            onQueryChange = viewModel::onQueryChange,
            onSearch = viewModel::onQueryChange
        )
        SearchImagesResultContent(query, viewState) { viewModel.onToggleFavorite(it) }
    }
    LifecycleStartEffect(key1 = viewModel, lifecycleOwner = LocalLifecycleOwner.current) {
        viewModel.init()
        onStopOrDispose { }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchImagesScreenPreview() {
    FindAPicTheme {
        SearchImagesScreen()
    }
}
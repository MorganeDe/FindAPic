package com.example.findapic.ui.search

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.findapic.R
import com.example.findapic.ui.theme.FindAPicTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImagesSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
) {
    SearchBar(
        inputField = {
            SearchBarDefaults.InputField(
                query = query,
                onQueryChange = onQueryChange,
                onSearch = onSearch,
                expanded = false,
                onExpandedChange = { },
                placeholder = { Text(text = stringResource(R.string.search_image_bar_placeholder)) },
                leadingIcon = {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = stringResource(
                            R.string.search_image_bar_search_icon_content_description
                        ),
                    )
                },
            )
        },
        expanded = false,
        onExpandedChange = { },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) { }
}

@Preview(showBackground = true)
@Composable
fun ImagesSearchBarPreview() {
    FindAPicTheme {
        ImagesSearchBar(
            query = "",
            onQueryChange = { },
            onSearch = { },
        )
    }
}
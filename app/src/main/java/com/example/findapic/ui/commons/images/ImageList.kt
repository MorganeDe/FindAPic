package com.example.findapic.ui.commons.images

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.findapic.ui.theme.FindAPicTheme

@Composable
fun ImageList(imageItems: List<UiImageItem>) {
    Column(modifier = Modifier.fillMaxSize()) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            items(imageItems) { imageItem ->
                ImageCard(imageItem)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ImageListPreview() {
    FindAPicTheme {
        ImageList(imageItems)
    }
}

val imageItems = listOf(
    UiImageItem(
        id = 0,
        source = "https://images.pexels.com/photos/2014422/pexels-photo-2014422.jpeg?auto=compress&cs=tinysrgb&h=130",
        contentDescription = "Brown Rocks During Golden Hour",
        imagePageLink = "https://www.pexels.com/photo/brown-rocks-during-golden-hour-2014422/",
        photographer = "Joey Farina",
    ),
    UiImageItem(
        id = 1,
        source = "https://images.pexels.com/photos/2014422/pexels-photo-2014422.jpeg?auto=compress&cs=tinysrgb&h=130",
        contentDescription = "Brown Rocks During Golden Hour",
        imagePageLink = "https://www.pexels.com/photo/brown-rocks-during-golden-hour-2014422/",
        photographer = "Joey Farina",
    ),
    UiImageItem(
        id = 2,
        source = "https://images.pexels.com/photos/2014422/pexels-photo-2014422.jpeg?auto=compress&cs=tinysrgb&h=130",
        contentDescription = "Brown Rocks During Golden Hour",
        imagePageLink = "https://www.pexels.com/photo/brown-rocks-during-golden-hour-2014422/",
        photographer = "Joey Farina",
    ),
)
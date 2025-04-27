package com.example.findapic.ui.commons.images

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.findapic.R
import com.example.findapic.ui.theme.FindAPicTheme

@Composable
fun ImageCard(imageItem: UiImageItem, onFavoriteButtonClick: (UiImageItem) -> Unit) {
    Box {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .clip(shape = RoundedCornerShape(8.dp))
                .background(color = MaterialTheme.colorScheme.secondaryContainer),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = imageItem.source,
                contentDescription = imageItem.contentDescription,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(top = 16.dp)
                    .size(100.dp)
                    .clip(RoundedCornerShape(8.dp)),
                onError = { Log.e("ImageCard", "Error loading image: ${it.result.throwable}") }
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = stringResource(
                    R.string.image_card_photographer_credit,
                    imageItem.photographer
                ),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                modifier = Modifier.padding(16.dp)
            )
        }
        IconToggleButton(
            checked = imageItem.isFavorite,
            onCheckedChange = {
                onFavoriteButtonClick(imageItem.copy(isFavorite = it))
            },
            modifier = Modifier.align(Alignment.TopEnd)
        ) {
            Icon(
                imageVector = getFavoriteVector(imageItem.isFavorite),
                contentDescription = stringResource(R.string.image_card_favorite_icon_content_description),
            )
        }
    }
}

private fun getFavoriteVector(isFavoriteImage: Boolean) =
    if (isFavoriteImage) Icons.Default.Favorite else Icons.Default.FavoriteBorder

@Preview(showBackground = true)
@Composable
fun ImageCardPreview() {
    FindAPicTheme {
        ImageCard(imageItems.first()) { }
    }
}
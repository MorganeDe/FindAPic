package com.example.findapic.data.models

import com.example.findapic.data.local.models.LocalImage
import com.example.findapic.data.local.models.toImageResource
import com.example.findapic.data.local.models.toImageResources
import com.example.findapic.data.local.models.toLocalImage
import com.example.findapic.domain.models.ImageResource
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class LocalImageExtensionsTest {
    @Test
    fun `toImageResource should return corresponding ImageResource`() {
        val localImage = LocalImage(
            id = 0,
            source = "https://images.pexels.com/photos/2014422/pexels-photo-2014422.jpeg?auto=compress&cs=tinysrgb&h=130",
            description = "Brown Rocks During Golden Hour",
            imagePageUrl = "https://www.pexels.com/photo/brown-rocks-during-golden-hour-2014422/",
            photographer = "Joey Farina",
            isFavorite = true,
        )

        assertThat(localImage.toImageResource()).isEqualTo(
            ImageResource(
                id = 0,
                source = "https://images.pexels.com/photos/2014422/pexels-photo-2014422.jpeg?auto=compress&cs=tinysrgb&h=130",
                description = "Brown Rocks During Golden Hour",
                imagePageUrl = "https://www.pexels.com/photo/brown-rocks-during-golden-hour-2014422/",
                photographer = "Joey Farina",
                isFavorite = true,
            )
        )
    }

    @Test
    fun `toImageResources should return corresponding list of ImageResource`() {
        val localImages = listOf(
            LocalImage(
                id = 0,
                source = "https://images.pexels.com/photos/2014422/pexels-photo-2014422.jpeg?auto=compress&cs=tinysrgb&h=130",
                description = "Brown Rocks During Golden Hour",
                imagePageUrl = "https://www.pexels.com/photo/brown-rocks-during-golden-hour-2014422/",
                photographer = "Joey Farina",
                isFavorite = true,
            ),
        )

        assertThat(localImages.toImageResources()).isEqualTo(
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
    }

    @Test
    fun `toLocalImage should return corresponding LocalImage`() {
        val imageResource = ImageResource(
            id = 0,
            source = "https://images.pexels.com/photos/2014422/pexels-photo-2014422.jpeg?auto=compress&cs=tinysrgb&h=130",
            description = "Brown Rocks During Golden Hour",
            imagePageUrl = "https://www.pexels.com/photo/brown-rocks-during-golden-hour-2014422/",
            photographer = "Joey Farina",
            isFavorite = true,
        )

        assertThat(imageResource.toLocalImage()).isEqualTo(
            LocalImage(
                id = 0,
                source = "https://images.pexels.com/photos/2014422/pexels-photo-2014422.jpeg?auto=compress&cs=tinysrgb&h=130",
                description = "Brown Rocks During Golden Hour",
                imagePageUrl = "https://www.pexels.com/photo/brown-rocks-during-golden-hour-2014422/",
                photographer = "Joey Farina",
                isFavorite = true,
            )
        )
    }
}
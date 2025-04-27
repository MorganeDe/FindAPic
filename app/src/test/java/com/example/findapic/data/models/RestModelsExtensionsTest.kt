package com.example.findapic.data.models

import com.example.findapic.data.rest.models.RestImage
import com.example.findapic.data.rest.models.RestSource
import com.example.findapic.data.rest.models.toImageResource
import com.example.findapic.domain.models.ImageResource
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class RestModelsExtensionsTest {

    @Test
    fun `toImageResource should return ImageResource from RestImage`() {
        val restImage = RestImage(
            id = 0,
            source = RestSource(
                url = "https://images.pexels.com/photos/2014422/pexels-photo-2014422.jpeg?auto=compress&cs=tinysrgb&h=130"
            ),
            description = "Brown Rocks During Golden Hour",
            imagePageUrl = "https://www.pexels.com/photo/brown-rocks-during-golden-hour-2014422/",
            photographer = "Joey Farina",
        )

        assertThat(restImage.toImageResource()).isEqualTo(
            ImageResource(
                id = 0,
                source = "https://images.pexels.com/photos/2014422/pexels-photo-2014422.jpeg?auto=compress&cs=tinysrgb&h=130",
                description = "Brown Rocks During Golden Hour",
                imagePageUrl = "https://www.pexels.com/photo/brown-rocks-during-golden-hour-2014422/",
                photographer = "Joey Farina",
                isFavorite = false,
            )
        )
    }
}
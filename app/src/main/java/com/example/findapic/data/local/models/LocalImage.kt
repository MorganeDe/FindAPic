package com.example.findapic.data.local.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LocalImage(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "source") val source: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "photographer") val photographer: String,
    @ColumnInfo(name = "image_page_url") val imagePageUrl: String,
    @ColumnInfo(name = "is_favorite") val isFavorite: Boolean,
)
package com.example.findapic.data.local.apis

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.findapic.data.local.models.LocalImage

@Dao
interface ImageDao {

    @Upsert
    suspend fun upsertLocalImage(image: LocalImage)

    @Query("SELECT * FROM LocalImage WHERE id = :id")
    suspend fun getLocalImageByIdOrNull(id: Int): LocalImage?

    @Query("DELETE FROM LocalImage WHERE id = :id")
    suspend fun deleteLocalImageById(id: Int)

    @Query("SELECT * FROM LocalImage WHERE is_favorite = 1")
    suspend fun getAllFavoriteLocalImages(): List<LocalImage>
}
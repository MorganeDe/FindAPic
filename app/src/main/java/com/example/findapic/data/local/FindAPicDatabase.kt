package com.example.findapic.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.findapic.data.local.apis.ImageDao
import com.example.findapic.data.local.models.LocalImage

@Database(entities = [LocalImage::class], version = 1)
abstract class FindAPicDatabase : RoomDatabase() {
    abstract fun imageDao(): ImageDao
}
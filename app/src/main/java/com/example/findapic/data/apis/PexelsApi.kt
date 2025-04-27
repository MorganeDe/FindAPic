package com.example.findapic.data.apis

import com.example.findapic.data.models.RestImages
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PexelsApi {
    @GET("search")
    suspend fun searchImages(@Query("query") query: String): Response<RestImages>
}
package com.example.findapic.di

import androidx.room.Room
import com.example.findapic.BuildConfig
import com.example.findapic.data.local.FindAPicDatabase
import com.example.findapic.data.local.apis.ImageDao
import com.example.findapic.data.rest.apis.PexelsApi
import com.example.findapic.data.services.ImagesRepositoryImpl
import com.example.findapic.domain.repositories.ImagesRepository
import com.example.findapic.domain.usecases.GetAllFavoriteImagesUseCase
import com.example.findapic.domain.usecases.GetAllFavoriteImagesUseCaseImpl
import com.example.findapic.domain.usecases.SearchImagesUseCase
import com.example.findapic.domain.usecases.SearchImagesUseCaseImpl
import com.example.findapic.domain.usecases.ToggleFavoriteImageUseCase
import com.example.findapic.domain.usecases.ToggleFavoriteImageUseCaseImpl
import com.example.findapic.ui.favorites.FavoriteImagesViewModel
import com.example.findapic.ui.search.SearchImagesViewModel
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val appModule = module {
    // OkHttp Client
    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .addInterceptor { chain ->
                val requestWithApiKey = chain.request()
                    .newBuilder()
                    .addHeader(PEXELS_API_AUTHORIZATION_HEADER, BuildConfig.PEXELS_API_KEY)
                    .build()
                chain.proceed(requestWithApiKey)
            }
            .build()
    }

    // Retrofit client
    single {
        val jsonMediaType = "application/json".toMediaType()

        Retrofit.Builder()
            .baseUrl(BuildConfig.PEXELS_API_BASE_URL)
            .addConverterFactory(
                get<Json>().asConverterFactory(jsonMediaType)
            )
            .client(get())
            .build()
    }

    // Apis
    single { get<Retrofit>().create(PexelsApi::class.java) }

    // Kotlin serialization json reader
    single<Json> {
        Json {
            ignoreUnknownKeys = true
        }
    }

    // Room database
    single<FindAPicDatabase> {
        Room.databaseBuilder(
            androidContext().applicationContext,
            FindAPicDatabase::class.java,
            "find_a_pic_database",
        ).build()
    }

    // Image DAO
    single<ImageDao> { get<FindAPicDatabase>().imageDao() }

    // Repositories
    single<ImagesRepository> { ImagesRepositoryImpl(get(), get()) }

    // Use cases
    single<SearchImagesUseCase> { SearchImagesUseCaseImpl(get()) }
    single<GetAllFavoriteImagesUseCase> { GetAllFavoriteImagesUseCaseImpl(get()) }
    single<ToggleFavoriteImageUseCase> { ToggleFavoriteImageUseCaseImpl(get()) }

    // View Models
    viewModel { SearchImagesViewModel(get(), get()) }
    viewModel { FavoriteImagesViewModel(get(), get()) }
}

private const val PEXELS_API_AUTHORIZATION_HEADER = "Authorization"
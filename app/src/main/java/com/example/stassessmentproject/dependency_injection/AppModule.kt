package com.example.stassessmentproject.dependency_injection

import android.content.Context
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.example.stassessmentproject.data.remote.MakeupAPI
import com.example.stassessmentproject.data.repository.MakeupRepositoryImpl
import com.example.stassessmentproject.domain.models.Validation
import com.example.stassessmentproject.domain.repository.MakeupRepository
import com.example.stassessmentproject.domain.usecases.GetMakeupProducts
import com.example.stassessmentproject.domain.usecases.ValidateUsername
import com.example.stassessmentproject.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    /** A function to provide a single Context's instance of the application throughout our app */
    @Provides
    @Singleton
    fun provideContextInstance(@ApplicationContext context: Context) = context

    @OptIn(ExperimentalSerializationApi::class)
    @Provides
    @Singleton
    fun provideMakeupAPI(): MakeupAPI {
        val contentType = "application/json".toMediaType()
        val json = Json {
            ignoreUnknownKeys = true
        }

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
            .create(MakeupAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideGetMakeupProductsUseCase(repository: MakeupRepository): GetMakeupProducts {
        return GetMakeupProducts(repository)
    }

    @Provides
    @Singleton
    fun provideAlbumRepository(
        api: MakeupAPI
    ): MakeupRepository {
        return MakeupRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideValidation(): Validation {
        return Validation(
            validateUsername = ValidateUsername()
        )
    }

}
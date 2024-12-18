package com.fayinterview.app.data

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    @Provides
    fun provideBaseUrl() = "https://node-api-for-candidates.onrender.com"

    @Provides
    @Singleton
    fun provideRetrofit(
        baseUrl: String
    ): Retrofit {
        val timeoutInterceptor = TimeoutInterceptor(retryAttempts = 3) // TODO: Nice to have - refine the handling of retries or timeout errors

        val client = OkHttpClient.Builder()
            .addInterceptor(timeoutInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun provideFayService(
        retrofit: Retrofit
    ): FayService = retrofit.create(FayService::class.java)
}

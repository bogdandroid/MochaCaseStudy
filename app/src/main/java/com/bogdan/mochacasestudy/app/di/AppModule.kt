package com.bogdan.mochacasestudy.app.di

import android.provider.SyncStateContract
import com.bogdan.mochacasestudy.app.Constants
import com.bogdan.mochacasestudy.app.Constants.BASE_URL
import com.bogdan.mochacasestudy.app.Constants.BASE_URL_IMAGES
import com.bogdan.mochacasestudy.app.data.api.ImageService
import com.bogdan.mochacasestudy.app.data.api.WeatherService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder().build()

    @Singleton
    @Provides
    @WeatherRetrofit
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()


    @Provides
    @Singleton
    fun provideWeatherService(@WeatherRetrofit retrofit: Retrofit) =
        retrofit.create(WeatherService::class.java)

    @Singleton
    @Provides
    @ImageRetrofit
    fun provideRetrofitForImage(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL_IMAGES)
            .client(okHttpClient)
            .build()


    @Provides
    @Singleton
    fun provideImageService(@ImageRetrofit retrofit: Retrofit) =
        retrofit.create(ImageService::class.java)
}


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class WeatherRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ImageRetrofit

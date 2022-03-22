package com.bogdan.mochacasestudy.app.data.api

import com.bogdan.mochacasestudy.app.Constants
import com.bogdan.mochacasestudy.app.data.model.ImageModel
import com.bogdan.mochacasestudy.app.data.model.WeatherEvent
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ImageService {

    @GET(Constants.IMAGES_ENDPOINT)
    suspend fun getWeatherEvents(@Query("limit") limit: Int): Response<List<ImageModel>>
}
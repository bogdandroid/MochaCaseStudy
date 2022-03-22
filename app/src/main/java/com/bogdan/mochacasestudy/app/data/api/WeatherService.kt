package com.bogdan.mochacasestudy.app.data.api

import com.bogdan.mochacasestudy.app.Constants
import com.bogdan.mochacasestudy.app.data.model.AffectedZone
import com.bogdan.mochacasestudy.app.data.model.WeatherEvent
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface WeatherService {

    @GET(Constants.ACTIVE_ALERTS_ENDPOINT)
    suspend fun getWeatherEvents(): Response<WeatherEvent>

    @GET
    suspend fun getAffectedZone(@Url endpoint: String): Response<AffectedZone>
}
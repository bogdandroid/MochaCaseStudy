package com.bogdan.mochacasestudy.app.data

import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val remoteDataSource: WeatherRemoteDataSource
) {
    suspend fun getWeatherFeatures() = remoteDataSource.getWeatherEvents()

    suspend fun getAffectedZones(urlList: List<String>) = remoteDataSource.getAffectedZones(urlList)

}
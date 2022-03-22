package com.bogdan.mochacasestudy.app.data

import android.util.Log
import com.bogdan.mochacasestudy.app.Constants
import com.bogdan.mochacasestudy.app.data.api.ImageService
import com.bogdan.mochacasestudy.app.data.api.WeatherService
import com.bogdan.mochacasestudy.app.data.model.AffectedZone
import com.bogdan.mochacasestudy.app.data.model.Feature
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL
import javax.inject.Inject

class WeatherRemoteDataSource @Inject constructor(
    private val weatherService: WeatherService
) {

    suspend fun getWeatherEvents(): Flow<List<Feature>> {
        return flow {
            weatherService.getWeatherEvents().body()?.let {
                emit(it.features)
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getAffectedZones(urlList: List<String>): Flow<List<AffectedZone>> {
        return coroutineScope {
            val deferredList = mutableListOf<Deferred<Response<AffectedZone>>>()
            urlList.forEach {
                deferredList.add(
                    async {
                        weatherService.getAffectedZone(it)
                    }
                )
            }
            deferredList.awaitAll().let { list ->
                val newList = list.mapNotNull {
                    it.body()
                }
                return@coroutineScope flow {
                    emit(newList)
                }
            }

        }
    }

}
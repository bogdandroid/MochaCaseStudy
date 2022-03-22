package com.bogdan.mochacasestudy.app.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bogdan.mochacasestudy.app.Constants
import com.bogdan.mochacasestudy.app.data.WeatherRepository
import com.bogdan.mochacasestudy.app.data.model.Feature
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val weatherRepository: WeatherRepository) :
    ViewModel() {

    private var getWeatherEvents: Job? = null

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e(HomeViewModel::class.simpleName, throwable.message ?: "")
    }

    private val _uiState = MutableStateFlow(HomeScreenState())
    val uiState: StateFlow<HomeScreenState> = _uiState.asStateFlow()

    init {
        _uiState.update {
            it.copy(isLoading = true)
        }
        getWeatherEvents?.cancel()
        getWeatherEvents = viewModelScope.launch(exceptionHandler) {
            weatherRepository.getWeatherFeatures().collect { features: List<Feature> ->
                _uiState.update {
                    it.copy(isLoading = false, featureList = features)
                }
            }
        }
    }
}

data class HomeScreenState(
    val isLoading: Boolean = false,
    val featureList: List<Feature> = emptyList(),
)
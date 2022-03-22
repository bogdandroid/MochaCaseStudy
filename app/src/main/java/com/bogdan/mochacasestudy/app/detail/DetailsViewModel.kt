package com.bogdan.mochacasestudy.app.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bogdan.mochacasestudy.app.data.WeatherRepository
import com.bogdan.mochacasestudy.app.data.model.AffectedZone
import com.bogdan.mochacasestudy.app.data.model.Feature
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e("DetailsViewModel", "error: $throwable")
    }

    fun initialize(feature: Feature?) {
        feature?.let { ft ->
            _uiState.update {
                it.copy(isLoading = true)
            }
            viewModelScope.launch(exceptionHandler) {
                weatherRepository.getAffectedZones(ft.properties.affectedZones)
                    .collect { zonesList ->
                        _uiState.update {
                            it.copy(isLoading = false, feature = ft, affectedZones = zonesList)
                        }
                    }
            }
        }
    }

    private val _uiState = MutableStateFlow(DetailsScreenState())
    val uiState: StateFlow<DetailsScreenState> = _uiState.asStateFlow()

}

data class DetailsScreenState(
    val isLoading: Boolean = false,
    val feature: Feature? = null,
    val affectedZones: List<AffectedZone> = emptyList()
)
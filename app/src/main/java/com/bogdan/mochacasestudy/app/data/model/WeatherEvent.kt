package com.bogdan.mochacasestudy.app.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import javax.inject.Inject

data class WeatherEvent(
    val type: String?,
    val title: String?,
    val updated: String?,
    val features: List<Feature>
)

@Parcelize
data class Feature @Inject constructor(
    val id: String?,
    val type: String?,
    val properties: Properties
) : Parcelable {
    var imageUrl: String? = null
}

@Parcelize
data class Properties(
    val onset: String?,
    val ends: String?,
    val event: String?,
    val senderName: String?,
    val severity: String?,
    val certainty: String?,
    val urgency: String?,
    val description: String?,
    val instruction: String?,
    val affectedZones: List<String>
) : Parcelable
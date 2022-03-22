package com.bogdan.mochacasestudy.app.data.model

data class AffectedZone(
    val id: String,
    val properties: ZoneProperties
)

data class ZoneProperties(
    val name: String,
    val state: String?,
    val radarStation: String?,
)
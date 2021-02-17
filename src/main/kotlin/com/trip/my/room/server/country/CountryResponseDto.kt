package com.trip.my.room.server.country

import java.util.*

data class CountryResponseDto(
    val id: UUID?,
    val name: String?,
    val type: String?,
    val flagImageUrl: String?,
    val letterImageUrl: String?,
) {}

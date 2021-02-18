package com.trip.my.room.server.country

import java.util.*

data class CountryResponseDto(
    val id: UUID?,
    val name: String?,
    val type: String?,
    val mainFood: String?,
    val flagImageUrl: String?,
    val albumStickerImageUrl: String?,
    val stampImageUrl: String?
) {}

package com.trip.my.room.server.domain.picture

import com.trip.my.room.server.common.enum.PictureOrder

data class PictureRequestDto (
    val order: PictureOrder,
    val url: String,
    val fileName: String,
    val storageKey: String
) {}

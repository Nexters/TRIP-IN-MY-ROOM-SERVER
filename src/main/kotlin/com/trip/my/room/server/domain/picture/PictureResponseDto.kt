package com.trip.my.room.server.domain.picture

import java.util.*

class PictureResponseDto(
    val id: UUID?,
    var order: Int?,
    var url: String?,
    val fileName: String?,
    val storageKey: String?,
    val story: UUID?
) {}

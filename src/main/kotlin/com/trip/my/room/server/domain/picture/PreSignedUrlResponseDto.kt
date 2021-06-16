package com.trip.my.room.server.domain.picture

import java.net.URL

data class PreSignedUrlResponseDto(
    val fileName: String,
    val storageKey: String,
    val url: String,
    val preSignedUrl: URL
) {}

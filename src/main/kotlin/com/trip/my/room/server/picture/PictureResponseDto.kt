package com.trip.my.room.server.picture

class PictureResponseDto(
    val id: Long,
    var order: Long,
    var url: String,
    val fileName: String,
    val storyId: Long
) {
}

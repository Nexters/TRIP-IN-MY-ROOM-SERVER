package com.trip.my.room.server.country

import java.util.*

data class CountryStoryCountResponseDto(
    var id: UUID?,
    var name: String?,
    var type: String?,
    var numberOfStories: Long?,
    var flagImageUrl: String?,
    var letterImageUrl: String?,
) {}

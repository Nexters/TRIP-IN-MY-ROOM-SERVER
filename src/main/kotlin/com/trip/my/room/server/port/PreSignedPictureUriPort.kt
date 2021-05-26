package com.trip.my.room.server.port

import java.net.URL

interface PreSignedPictureUriPort {

    fun getPreSignedPictureUrl(storageKey: String): URL

}

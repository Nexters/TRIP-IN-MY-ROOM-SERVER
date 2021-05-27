package com.trip.my.room.server.application.port.out

import java.net.URL

interface FindPreSignedUriPort {

    fun findPreSignedUrl(storageKey: String): URL

}

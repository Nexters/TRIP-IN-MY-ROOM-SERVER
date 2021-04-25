package com.trip.my.room.server.port

import java.net.URL

interface PreSignedPictureUri {

    fun getPreSignedPictureUrls(fileNameList: List<String>): List<URL>

}

package com.trip.my.room.server.application.port.out

interface DeletePictureByStorageKeyPort {

    fun deletePictureByStorageKey(storageKey: String)

}
package com.trip.my.room.server.application.port.out

interface DeletePictureByStorageKeyListPort {

    fun deletePictureByStorageKeyList(storageKeyCollection: Collection<String>)

}

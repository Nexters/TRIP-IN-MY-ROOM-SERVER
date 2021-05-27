package com.trip.my.room.server.application.port.out

interface DeleteAllObjectInStoragePort {

    fun deleteAllObjectInStorage(storageKeyCollection: Collection<String>)

}

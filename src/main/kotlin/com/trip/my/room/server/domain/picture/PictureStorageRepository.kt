package com.trip.my.room.server.domain.picture

import com.trip.my.room.server.common.storage.PictureStorageClient
import org.springframework.stereotype.Repository

@Repository
class PictureStorageRepository(
    private val pictureStorageClient: PictureStorageClient,
) {
    fun deletePicture(storageKey: String) {
        pictureStorageClient.deletePicture(storageKey)
    }

    fun deleteBulkPictures(storageKeyList: List<String?>) {
        pictureStorageClient.deleteBulkPictures(storageKeyList)
    }
}

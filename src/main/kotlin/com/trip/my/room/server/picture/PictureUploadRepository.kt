package com.trip.my.room.server.picture

import org.springframework.stereotype.Repository
import java.util.stream.Collectors

@Repository
class PictureUploadRepository (private val pictureUploadClient: PictureUploadClient) {

    fun getMappingPictureUrlWithOrder(pictureFiles: List<PictureFile>) {
        // 사진을 꼭 order 순서대로 저장
        pictureFiles.sortedBy { it.order }

        val orderedPictureFiles = pictureFiles.stream()
            .map { pictures -> pictures.picture }
            .collect(Collectors.toList())
        pictureUploadClient.uploadPictureList(orderedPictureFiles)

        // 사진 url, order를 서로 매핑해서 매핑 정보 리턴
    }

}

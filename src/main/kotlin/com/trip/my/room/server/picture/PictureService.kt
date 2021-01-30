package com.trip.my.room.server.picture

import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.util.*

@Service
class PictureService(private val pictureUploadRepository: PictureUploadRepository) {

    fun createNewPicture(pictureFiles: List<MultipartFile>): List<PictureResponseDto> {
        // upload files
        getMappedPictureWithOrder(pictureFiles)

        // db 접근해서 picture 만듬

        // PictureResponseDto 로 변환

        // return
        return Arrays.asList(PictureResponseDto(1, 1, "hello", "world", 1))
    }

    private fun getMappedPictureWithOrder(pictureFiles: List<MultipartFile>) {
        pictureUploadRepository.getMappingPictureUrlWithOrder(emptyList())
        // TODO: 업데이트한다고 생각했을 때 업로드하는 친구도 있고 안하는 친구도 있을 수 있다!!!
        TODO("Mapping order and picture")
    }
}

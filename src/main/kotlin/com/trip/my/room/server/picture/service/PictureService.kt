package com.trip.my.room.server.picture.service

import com.trip.my.room.server.common.enum.PictureOrder
import com.trip.my.room.server.picture.PictureFile
import com.trip.my.room.server.picture.PictureRepository
import com.trip.my.room.server.picture.PictureStorageRepository
import com.trip.my.room.server.picture.PictureUploadedUrl
import com.trip.my.room.server.picture.controller.PictureResponseDto
import com.trip.my.room.server.picture.domain.PictureEntity
import com.trip.my.room.server.story.domain.model.StoryEntity
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.util.*
import java.util.stream.IntStream
import javax.transaction.Transactional
import kotlin.streams.toList

@Service
class PictureService(
    private val pictureStorageRepository: PictureStorageRepository,
    private val pictureRepository: PictureRepository
) {
    fun getPictureListByStoryId(storyId: UUID?): List<PictureResponseDto> {
        return pictureRepository.findByStoryId(storyId).stream()
            .map { pictureEntity ->
                PictureResponseDto(
                    pictureEntity.id,
                    pictureEntity.pictureOrder?.getValue(),
                    pictureEntity.url,
                    pictureEntity.fileName,
                    storyId
                )
            }
            .toList()
    }

    fun createNewPicture(
        storyEntity: StoryEntity,
        multipartFileList: List<MultipartFile>
    ): List<PictureResponseDto> {
        val mappedPictureWithOrder = getMappedPictureWithOrder(storyEntity.id, multipartFileList)

        val savedPictureEntityList = mappedPictureWithOrder.stream()
            .map { pictureUrlWithOrder ->
                pictureRepository.save(convertPictureEntity(storyEntity, pictureUrlWithOrder))
            }
            .toList()

        return savedPictureEntityList.stream()
            .map { savedPictureEntity ->
                PictureResponseDto(
                    savedPictureEntity.id,
                    savedPictureEntity.pictureOrder?.getValue(),
                    savedPictureEntity.url,
                    savedPictureEntity.fileName,
                    storyEntity.id
                )
            }
            .toList()
    }

    private fun getMappedPictureWithOrder(
        storyId: UUID?,
        multipartFileList: List<MultipartFile>
    ): List<PictureUploadedUrl> {
        val pictureFiles = IntStream.range(0, multipartFileList.size).boxed()
            .map { i -> PictureFile(multipartFileList.get(i), PictureOrder.fromInt(i + 1)) }
            .toList()

        return pictureStorageRepository.getMappingPictureUrlWithOrder(storyId, pictureFiles)
    }

    private fun convertPictureEntity(
        storyEntity: StoryEntity,
        pictureUploadedUrl: PictureUploadedUrl
    ): PictureEntity {
        return PictureEntity(
            pictureUploadedUrl.order,
            pictureUploadedUrl.pictureUrl.toString(),
            pictureUploadedUrl.pictureUrl.path,
            storyEntity
        )
    }

    @Transactional
    fun deletePictureByStoryId(storyId: UUID) {
        // TODO 확장성 생각해서 storyId가 아닌 것으로 받을 수 있는지 생각
        val foundPictureEntityList = pictureRepository.findByStoryId(storyId)
        val targetRemovePicturePathList = foundPictureEntityList.stream()
            .map { pictureEntity -> pictureEntity.fileName?.removePrefix("/") }
            .toList()
        pictureStorageRepository.deleteBulkPictures(targetRemovePicturePathList)

        pictureRepository.deleteByStoryId(storyId)
    }
}

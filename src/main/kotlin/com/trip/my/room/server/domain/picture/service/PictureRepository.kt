package com.trip.my.room.server.domain.picture.service

import com.trip.my.room.server.common.enum.PictureOrder
import com.trip.my.room.server.domain.picture.*
import com.trip.my.room.server.story.domain.model.StoryEntity
import org.springframework.stereotype.Service
import java.util.*
import java.util.stream.IntStream
import javax.transaction.Transactional
import kotlin.streams.toList

@Service
class PictureRepository(
    private val pictureStorageRepository: PictureStorageRepository,
    private val pictureRepositoryPort: PictureRepositoryPort
) {
    fun getPictureListByStoryId(storyId: UUID?): List<PictureResponseDto> {
        return pictureRepositoryPort.findByStoryId(storyId).stream()
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
        pictureRequestDtoList: List<PictureRequestDto>
    ): List<PictureResponseDto> {
        val pictureEntityList = convertPictureEntityList(storyEntity, pictureRequestDtoList)

        val savedPictureEntityList = pictureEntityList.stream()
            .map { pictureEntity -> pictureRepositoryPort.save(pictureEntity) }
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

    private fun convertPictureEntityList(
        storyEntity: StoryEntity,
        pictureRequestDtoList: List<PictureRequestDto>
    ): List<PictureEntity> {
        return IntStream.range(0, pictureRequestDtoList.size).boxed()
            .map { i -> PictureEntity(PictureOrder.fromInt(i + 1),
                pictureRequestDtoList[i].url,
                pictureRequestDtoList[i].fileName,
                storyEntity) }
            .toList()
    }

    @Transactional
    fun deletePictureByStoryId(storyId: UUID) {
        // TODO 확장성 생각해서 storyId가 아닌 것으로 받을 수 있는지 생각

        // TODO 이전에 존재하는 사진 삭제하기
//        val foundPictureEntityList = pictureRepositoryPort.findByStoryId(storyId)
//        val targetRemovePicturePathList = foundPictureEntityList.stream()
//            .map { pictureEntity -> pictureEntity.fileName?.removePrefix("/") }
//            .toList()
//        pictureStorageRepository.deleteBulkPictures(targetRemovePicturePathList)

        pictureRepositoryPort.deleteByStoryId(storyId)
    }
}

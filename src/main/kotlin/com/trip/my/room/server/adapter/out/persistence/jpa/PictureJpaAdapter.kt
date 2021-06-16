package com.trip.my.room.server.adapter.out.persistence.jpa

import com.trip.my.room.server.adapter.out.persistence.jpa.entity.PictureEntity
import com.trip.my.room.server.adapter.out.persistence.jpa.entity.PictureRepository
import com.trip.my.room.server.adapter.out.persistence.jpa.entity.StoryEntity
import com.trip.my.room.server.application.port.out.CreatePicturePort
import com.trip.my.room.server.application.port.out.DeletePictureByStorageKeyPort
import com.trip.my.room.server.application.port.out.DeletePictureByStoryIdPort
import com.trip.my.room.server.application.port.out.FindAllPictureByStoryIdPort
import com.trip.my.room.server.domain.picture.PictureRequestDto
import com.trip.my.room.server.domain.picture.PictureResponseDto
import org.springframework.stereotype.Service
import java.util.*
import java.util.stream.IntStream
import javax.transaction.Transactional
import kotlin.streams.toList

@Service
class PictureJpaAdapter(
    private val pictureRepository: PictureRepository
) : CreatePicturePort, FindAllPictureByStoryIdPort, DeletePictureByStoryIdPort, DeletePictureByStorageKeyPort {

    override fun findAllPictureByStoryId(storyId: UUID?): List<PictureResponseDto> {
        return pictureRepository.findByStoryId(storyId).stream()
            .map { pictureEntity ->
                PictureResponseDto(
                    pictureEntity.id,
                    pictureEntity.pictureOrder?.getValue(),
                    pictureEntity.url,
                    pictureEntity.fileName,
                    pictureEntity.storageKey,
                    storyId
                )
            }
            .toList()
    }

    override fun createPicture(
        storyEntity: StoryEntity,
        pictureRequestDtoList: List<PictureRequestDto>
    ): List<PictureResponseDto> {
        val pictureEntityList = convertPictureEntityList(storyEntity, pictureRequestDtoList)

        val savedPictureEntityList = pictureEntityList.stream()
            .map { pictureEntity -> pictureRepository.save(pictureEntity) }
            .toList()

        return savedPictureEntityList.stream()
            .map { savedPictureEntity ->
                PictureResponseDto(
                    savedPictureEntity.id,
                    savedPictureEntity.pictureOrder?.getValue(),
                    savedPictureEntity.url,
                    savedPictureEntity.fileName,
                    savedPictureEntity.storageKey,
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
            .map { i ->
                PictureEntity(
                    pictureRequestDtoList[i].order,
                    pictureRequestDtoList[i].url,
                    pictureRequestDtoList[i].fileName,
                    pictureRequestDtoList[i].storageKey,
                    storyEntity
                )
            }
            .toList()
    }

    @Transactional
    override fun deletePictureByStorageKey(storageKey: String) {
        pictureRepository.deleteByStorageKey(storageKey)
    }

    @Transactional
    override fun deletePictureByStoryId(storyId: UUID) {
        // TODO Make deprecated
        pictureRepository.deleteByStoryId(storyId)
    }

}

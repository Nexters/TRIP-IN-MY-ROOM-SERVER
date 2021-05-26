package com.trip.my.room.server.domain.picture

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface PictureRepositoryPort : JpaRepository<PictureEntity, UUID> {

    fun findByStoryId(storyId: UUID?): List<PictureEntity>

    fun deleteByStoryId(storyId: UUID?)

}

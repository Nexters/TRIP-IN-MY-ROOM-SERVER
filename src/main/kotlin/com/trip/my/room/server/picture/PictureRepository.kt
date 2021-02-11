package com.trip.my.room.server.picture

import com.trip.my.room.server.picture.domain.PictureEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface PictureRepository : JpaRepository<PictureEntity, UUID> {

    fun findByStoryId(storyId: UUID?): List<PictureEntity>

    fun deleteByStoryId(storyId: UUID?)

}

package com.trip.my.room.server.story.domain.repository

import com.trip.my.room.server.story.domain.model.StoryEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface StoryRepository : JpaRepository<StoryEntity, UUID> {

    fun findByUserId(userId: UUID)

}

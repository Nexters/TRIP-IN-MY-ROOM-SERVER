package com.trip.my.room.server.story.domain.repository

import com.trip.my.room.server.story.domain.model.StoryEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface StoryRepository : JpaRepository<StoryEntity, Long> {
}

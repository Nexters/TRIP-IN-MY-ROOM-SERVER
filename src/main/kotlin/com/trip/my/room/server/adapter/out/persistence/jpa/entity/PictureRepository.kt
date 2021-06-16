package com.trip.my.room.server.adapter.out.persistence.jpa.entity

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface PictureRepository : JpaRepository<PictureEntity, UUID> {

    fun findByStoryId(storyId: UUID?): List<PictureEntity>

    fun deleteByStoryId(storyId: UUID?)

    fun deleteByStorageKey(storageKey: String?)

}

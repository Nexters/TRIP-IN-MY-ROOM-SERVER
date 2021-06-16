package com.trip.my.room.server.adapter.out.persistence.jpa.entity

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface StoryRepository : JpaRepository<StoryEntity, UUID> {

    fun findByUserId(userId: UUID): List<StoryEntity>

    fun findByUserIdAndCountryType(userId: UUID, countryType: String): List<StoryEntity>

    fun countByUserIdAndCountryType(userId: UUID, countryType: String?): Long

}

package com.trip.my.room.server.user

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : JpaRepository<UserEntity, UUID> {

	fun findByEmail(email: String) : Optional<UserEntity>
}
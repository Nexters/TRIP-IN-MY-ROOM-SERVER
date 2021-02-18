package com.trip.my.room.server.country

import com.trip.my.room.server.user.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CountryRepository : JpaRepository<CountryEntity, UUID>, CountryRepositoryCustom {
    fun findAllByType(type: String): List<CountryEntity>

    fun findByName(name: String): Optional<CountryEntity>

    fun findByNameAndType(name: String, type: String): CountryEntity

    fun countByUserAndType(userEntity: UserEntity, type: String): Long

    fun findByNameContainingAndTypeIsNot(name: String, type: String): List<CountryEntity>

    fun findByNameContainingAndUser(name: String, userEntity: UserEntity?): List<CountryEntity>

}

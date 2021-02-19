package com.trip.my.room.server.user.service

import com.trip.my.room.server.user.UserEntity
import com.trip.my.room.server.user.UserRepository
import com.trip.my.room.server.user.dto.UserDto
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*
import javax.persistence.EntityManager

@Service
class UserService(private val userRepository: UserRepository,
				  private val em: EntityManager
) {
	
	fun create(userEntity: UserEntity) {
		userRepository.save(userEntity)
	}
	
	fun join(userJoinIn: UserDto.UserJoinIn): UserEntity {
		var newUser: UserEntity = UserEntity().apply {
			this.email = userJoinIn.email
			this.name = userJoinIn.name
			this.social = userJoinIn.social
			this.socialId = userJoinIn.socialId
		}
		userRepository.save(newUser)
		return newUser
	}
	
	fun findByUserId(userId: UUID): UserDto.BasicInfoOut {
		val user = userRepository.findById(userId).get()
		return UserDto.BasicInfoOut().apply {
			this.name = user.name
			this.email = user.email
			this.social = user.social
		}
	}
	
	@Transactional
	fun updateByUserId(userId: UUID, updateIn: UserDto.UpdateIn): UserDto.BasicInfoOut {
		val user = userRepository.findById(userId).get()
		user.name = updateIn.name
		em.persist(user)
		em.flush()
		return UserDto.BasicInfoOut().apply {
			this.name = user.name
			this.email = user.email
			this.social = user.social
		}
	}
	
	fun findUserEntityByUserId(userId: UUID): UserEntity {
		return userRepository.findById(userId).get()
	}
	
	fun isExistEmail(userEmail: String): Boolean {
		var result = userRepository.findByEmail(userEmail)
		if (result.isPresent) {
			return true
		}
		return false
	}
	
	fun findByUserEmail(userEmail: String): UserEntity {
		return userRepository.findByEmail(userEmail).get()
	}
	
	fun deleteByUserId(userId: UUID) {
		return userRepository.deleteById(userId)
	}
}
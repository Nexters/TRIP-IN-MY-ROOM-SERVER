package com.trip.my.room.server.user.service

import com.trip.my.room.server.user.UserEntity
import com.trip.my.room.server.user.UserRepository
import com.trip.my.room.server.user.dto.UserDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService(@Autowired private val userRepository: UserRepository) {
	
	fun create(userEntity: UserEntity){
		userRepository.save(userEntity)
	}
	
	fun join(userJoinIn: UserDto.UserJoinIn): UserEntity {
		var newUser : UserEntity = UserEntity().apply {
			this.email = userJoinIn.email
			this.name = userJoinIn.name
			this.social = userJoinIn.social
			this.socialId = userJoinIn.socialId
		}
		userRepository.save(newUser)
		return newUser
	}
	
	fun findByUserId(userId : UUID): UserDto.BasicInfoOut {
		val user = userRepository.findById(userId).get()
		return UserDto.BasicInfoOut().apply {
			this.name = user.name
			this.email = user.email
			this.social = user.social
		}
	}
	
	fun isExistEmail(userEmail: String): Boolean {
		var result = userRepository.findByEmail(userEmail)
		if (result.isPresent){
			return true
		}
		return false
	}
	
	fun findByUserEmail(userEmail: String): UserEntity {
		return userRepository.findByEmail(userEmail).get()
	}
}
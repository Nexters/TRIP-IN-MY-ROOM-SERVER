package com.trip.my.room.server.user.service

import com.trip.my.room.server.user.UserEntity
import com.trip.my.room.server.user.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService(@Autowired private val userRepository: UserRepository) {
	
	fun create(userEntity: UserEntity){
		userRepository.save(userEntity)
	}
	
	fun join(userJoinIn: UserRequestDto.UserJoinIn): UserEntity {
		var newUser : UserEntity = UserEntity().apply {
			this.email = userJoinIn.email
			this.name = userJoinIn.name
			this.social = userJoinIn.social
			this.socialId = userJoinIn.socialId
		}
		userRepository.save(newUser)
		return newUser
	}
	
	fun findByUserId(userId : UUID): UserEntity {
		return userRepository.findById(userId).get()
	}
}
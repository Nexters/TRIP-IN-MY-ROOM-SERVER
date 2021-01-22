package com.trip.my.room.server.controller.user

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController {

    @GetMapping("/users/{id}")
    fun getUsers(@PathVariable id: Long): UserResponseDto {
        return UserResponseDto(id, "Hoon", "naver")
    }
}

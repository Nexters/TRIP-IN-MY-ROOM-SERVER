package com.trip.my.room.server.user

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController {

    @GetMapping("/{id}")
    fun getUsers(@PathVariable id: Long): UserResponseDto {
        return UserResponseDto(id, "Hoon", "hello@world", "naver")
    }

    @PatchMapping("/{id}")
    fun patchUsers(@RequestBody userPatchResponseDto: UserPatchResponseDto) {
        println(userPatchResponseDto)
    }

    // TODO: login, logout, join
}

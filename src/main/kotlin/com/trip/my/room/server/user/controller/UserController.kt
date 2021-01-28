package com.trip.my.room.server.user.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController {
    
    @PostMapping("/join")
    fun join(): ResponseEntity<Any>{
        return ResponseEntity.status(HttpStatus.OK).body("join")
    
    }
    
    @PostMapping("/login")
    fun login(): ResponseEntity<Any>{
        return ResponseEntity.status(HttpStatus.OK).body("login")
    }
    
    @PostMapping("/logout")
    fun logout(): ResponseEntity<Any>{
        return ResponseEntity.status(HttpStatus.OK).body("logout")
    }

    @GetMapping("/{id}")
    fun getUsers(@PathVariable id: Long): UserResponseDto {
        return UserResponseDto(id, "Hoon", "hello@world", "naver")
    }

    @PatchMapping("/{id}")
    fun patchUsers(@RequestBody userPatchResponseDto: UserPatchResponseDto) {
        println(userPatchResponseDto)
    }
}

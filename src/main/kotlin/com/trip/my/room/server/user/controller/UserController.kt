package com.trip.my.room.server.user.controller

import com.trip.my.room.server.common.auth.SocialLoginService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

@Controller
@RequestMapping("/users")
class UserController(@Autowired private val socialLoginService: SocialLoginService) {
    
    @GetMapping("/kakao/join")
    fun KakaoJoin(@RequestParam code : String,
                  @RequestParam state : String ?= null,
                  @RequestParam error : String ?= null,
                  @RequestParam errorDescription: String ?= null): ResponseEntity<Any>{
        
        // access_Token, refresh_token, Token Type
        var kakao_user = socialLoginService.getKakaoUser("kakao", code)
        return ResponseEntity.status(HttpStatus.OK).body(kakao_user)
    }
    
    @GetMapping("/login")
    fun login(@RequestBody redirectDto : SocialReDirectDto.KakaoDto): ResponseEntity<Any>{
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

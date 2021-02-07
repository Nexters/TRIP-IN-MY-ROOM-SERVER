package com.trip.my.room.server.user.controller

import com.trip.my.room.server.user.IfUserPrincipal
import com.trip.my.room.server.user.UserEntity
import com.trip.my.room.server.user.service.SocialLoginService
import com.trip.my.room.server.user.dto.UserDto
import com.trip.my.room.server.user.jwt.IfUserTokenService
import com.trip.my.room.server.user.service.UserService
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import java.util.*

@Controller
@RequestMapping("/users")
class UserController(@Autowired private val socialLoginService: SocialLoginService,
                     @Autowired private val userService: UserService,
                     @Autowired private val ifUserTokenService: IfUserTokenService) {
    
    @ApiOperation("소셜 로그인으로부터 Redirect 전용 API")
    @GetMapping("/login/{social}")
    fun socialJoin(@PathVariable social: String,
                  @RequestParam code : String,
                  @RequestParam state : String ?= null,
                  @RequestParam error : String ?= null,
                  @RequestParam errorDescription: String ?= null): ResponseEntity<Any>{
        
        // Get user info from social
        val userJoinIn : UserDto.UserJoinIn = socialLoginService.getSocialUser(social, code)!!
        var user: UserEntity? = null
        
        // join or get user info from DB
        var registered = userService.isExistEmail(userJoinIn.email!!)
        if (!registered) user =  userService.join(userJoinIn) else user = userService.findByUserEmail(userJoinIn.email!!)
        
        // create our service token
        val jwt: MutableMap<String, Any>  = ifUserTokenService.createToken(user)
        return ResponseEntity.status(HttpStatus.OK).body(jwt)
    }
    
    @ApiOperation("사용자 정보 가져오기(토큰을 기반)")
    @GetMapping("")
    fun getUserInfo(@AuthenticationPrincipal principal: IfUserPrincipal): ResponseEntity<UserDto.BasicInfoOut> {
        var userId: UUID = principal.getUserUUID()
        var userInfo: UserDto.BasicInfoOut = userService.findByUserId(userId)
        return ResponseEntity.status(HttpStatus.OK).body(userInfo)
    }
    
    @ApiOperation("로그아웃")
    @PostMapping("/logout")
    fun logout(): ResponseEntity<Any>{
        // remove refresh token
        return ResponseEntity.status(HttpStatus.OK).body("logout")
    }

}

package com.trip.my.room.server.place

import com.trip.my.room.server.user.IfUserPrincipal
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/place")
class PlaceController(@Autowired private val placeService: PlaceService) {

    @ApiOperation("New Place 생성하기 (해당 정보의 Place 생성, 사용하지 않는 값들은 null로 처리)")
    @PostMapping
    fun createPlace(
        @AuthenticationPrincipal principal: IfUserPrincipal,
        @RequestBody placeIn: PlaceDto.PlaceIn
    ): ResponseEntity<Any> {
        val result = placeService.createCustomPlace(principal.getUserUUID(), placeIn)
        return ResponseEntity.status(HttpStatus.CREATED).body(result)
    }

    // TODO Custom Place 삭제 기능 추가 (관련 Story도 함께 삭제)
}

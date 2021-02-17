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

    @ApiOperation("Place 정보 가져오기 (유저가 생성한 장소를 포함해서 조회)")
    @GetMapping
    fun getPlace(@AuthenticationPrincipal principal: IfUserPrincipal): List<PlaceDto.PlaceOut> {
        return placeService.getPlaces(principal.getUserUUID())
    }

    @ApiOperation("Place 검색하기 (RequestParam으로 place_str에 해당하는 단어가 포함된 Place 조회)")
    @GetMapping("/search")
    fun getPlaceBySearch(
        @RequestParam(
            name = "place_str",
            required = true
        ) placeStr: String
    ): List<PlaceDto.PlaceOut> {
        return placeService.searchPlaceByPlaceName(placeStr)
    }

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

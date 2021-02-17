package com.trip.my.room.server.place

import com.trip.my.room.server.user.IfUserPrincipal
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

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
	fun getPlaceBySearch(@RequestParam(name = "place_str", required = true) placeStr: String): List<PlaceDto.PlaceOut> {
		return placeService.searchPlaceByPlaceName(placeStr)
	}
	
	
	// Custom Place 삭제 기능 추가 (관련 Story도 함께 삭제)
}

package com.trip.my.room.server.country

import java.util.*

class CountryDto {
	
	open class CountryIn() {
		var name: String? = null
	}
	
	open class CountryOut() {
		var id: UUID? = null
		var name: String? = null
		var type: String? = null
		var mainFood: String? = null
		var flagImageUrl: String? = null
		var albumStickerImageUrl: String? = null
		var stampImageUrl: String? = null
	}

}

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
		var flagImageUrl: String? = null
		var letterImageUrl: String? = null
	}
	
	open class CountryWithStoryCountOut() {
		var id: UUID? = null
		var name: String? = null
		var type: String? = null
		var numberOfStories: Long? = null
		var flagImageUrl: String? = null
		var letterImageUrl: String? = null
	}
}
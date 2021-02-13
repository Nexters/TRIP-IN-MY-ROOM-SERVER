package com.trip.my.room.server.country

import java.util.*

interface CountryRepositoryCustom {
	
	fun finCountryById(countryId : UUID): CountryEntity?
}
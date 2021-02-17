package com.trip.my.room.server.country

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CountryService(@Autowired private val countryRepository: CountryRepository,
					 @Autowired private val countryMapper: CountryMapper) {
	
	fun getCountries(): List<CountryDto.CountryOut> {
		val result = countryRepository.findAll()
		return countryMapper.toDtoList(result)
	}
	
	fun getCustomCountry(): CountryEntity {
		return countryRepository.findCustomCountry()
	}
}
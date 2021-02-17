package com.trip.my.room.server.country

import org.mapstruct.Mapper
import org.springframework.stereotype.Component
import kotlin.streams.toList

@Mapper
@Component
class CountryMapper {
	
	fun toDto(countryEntity: CountryEntity): CountryDto.CountryOut {
		return CountryDto.CountryOut().apply {
			this.id = countryEntity.id
			this.name = countryEntity.name
			this.countryIcon = countryEntity.countryIcon
		}
	}
	
	fun toDtoList(countryEntityList: MutableList<CountryEntity>): List<CountryDto.CountryOut> {
		return countryEntityList.stream().map { countryEntity ->
			toDto(countryEntity)
		}.toList()
	}
}
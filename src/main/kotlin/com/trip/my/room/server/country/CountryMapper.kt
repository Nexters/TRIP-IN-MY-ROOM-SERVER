package com.trip.my.room.server.country

import org.mapstruct.Mapper
import org.springframework.stereotype.Component

@Mapper
@Component
class CountryMapper {
	fun toDto(countryEntity: CountryEntity): CountryDto.CountryOut {
		return CountryDto.CountryOut().apply {
			this.id = countryEntity.id
			this.name = countryEntity.name
			this.type = countryEntity.type
			this.mainFood = countryEntity.mainFood
			this.flagImageUrl = countryEntity.flagImageUrl
			this.albumStickerImageUrl = countryEntity.albumStickerImageUrl
			this.stampImageUrl = countryEntity.stampImageUrl
		}
	}
	
	fun toDtoList(countryEntityList: MutableList<CountryEntity>): List<CountryDto.CountryOut> {
		return countryEntityList.map { countryEntity -> toDto(countryEntity) }
	}
}

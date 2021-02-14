package com.trip.my.room.server.place

import org.mapstruct.Mapper
import org.springframework.stereotype.Component

@Mapper
@Component
class PlaceMapper {
	
	
	fun toDto(placeEntity: PlaceEntity): PlaceDto.PlaceOut {
		return PlaceDto.PlaceOut().apply {
			this.id = placeEntity.id
			this.name = placeEntity.name
			this.countryId =  if (placeEntity.country != null) placeEntity.country!!.id else null
			this.imageIcon = placeEntity.imageIcon
		}
	}
	
	fun toDtoList(placeEntityList: MutableList<PlaceEntity>): List<PlaceDto.PlaceOut> {
		return placeEntityList.map { placeEntity -> toDto(placeEntity) }
	}
}
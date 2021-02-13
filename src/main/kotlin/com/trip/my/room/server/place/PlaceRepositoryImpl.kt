package com.trip.my.room.server.place

import com.querydsl.jpa.impl.JPAQueryFactory
import com.trip.my.room.server.place.QPlaceEntity.placeEntity
import org.springframework.beans.factory.annotation.Autowired
import java.util.*


class PlaceRepositoryImpl(@Autowired private val query: JPAQueryFactory): PlaceRepositoryCustom {
	
	override fun findAllByUserId(userId: UUID): MutableList<PlaceEntity>? {
		// customized가 false인 모든 place와 함께
		// customized가 true면 유저의 id가 userId인 경우만 골라서
		return query.selectFrom(placeEntity)
				.where(placeEntity.customized.eq(false)
						.or(placeEntity.customized.eq(true).and(placeEntity.user.id.eq(userId))))
				.fetch()
	}
	
	override fun searchPlaceByPlaceName(placeStr: String): MutableList<PlaceEntity>? {
		val fetchResults = query.selectFrom(placeEntity)
				.where(placeEntity.name.like("%${placeStr}%"))
				.fetchResults()
		return fetchResults.results
	}
}
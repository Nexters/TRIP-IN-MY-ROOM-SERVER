package com.trip.my.room.server.place

import com.querydsl.jpa.impl.JPAQueryFactory
import com.trip.my.room.server.place.QPlaceEntity.placeEntity
import org.springframework.beans.factory.annotation.Autowired
import java.util.*


class PlaceRepositoryImpl(@Autowired private val query: JPAQueryFactory) : PlaceRepositoryCustom {

    override fun findByPlaceName(place: String): PlaceEntity? {
        val fetchResults = query.selectFrom(placeEntity)
            .where(placeEntity.name.eq(place))
            .fetchOne()
        return fetchResults!!
    }
}

package com.trip.my.room.server.country

import com.querydsl.jpa.impl.JPAQueryFactory
import com.trip.my.room.server.country.QCountryEntity.countryEntity
import org.springframework.beans.factory.annotation.Autowired
import java.util.*

class CountryRepositoryImpl(@Autowired private val query: JPAQueryFactory): CountryRepositoryCustom {
	
	override fun finCountryById(countryId: UUID): CountryEntity? {
		return query.selectFrom(countryEntity)
				.where(countryEntity.id.eq(countryId))
				.fetchOne()
	}
}
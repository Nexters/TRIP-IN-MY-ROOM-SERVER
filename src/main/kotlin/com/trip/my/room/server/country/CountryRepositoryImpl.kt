package com.trip.my.room.server.country

import com.querydsl.jpa.impl.JPAQueryFactory
import com.trip.my.room.server.country.QCountryEntity.countryEntity
import org.springframework.beans.factory.annotation.Autowired
import java.util.*

class CountryRepositoryImpl(@Autowired private val query: JPAQueryFactory) : CountryRepositoryCustom {
	
	override fun finCountryById(countryId: UUID): CountryEntity? {
		return query.selectFrom(countryEntity)
				.where(countryEntity.id.eq(countryId))
				.fetchOne()
	}
	
	override fun getCountriesByUserId(userId: UUID, others: Boolean): MutableList<CountryEntity> {
		var query = query.selectFrom(countryEntity)
		if (others) {
			// others도 포함이면 userId로 생성된 country와 type이 other가 아닌 모든 것들을 포함함
			query.where(countryEntity.user.id.eq(userId).or(countryEntity.type.notIn("others")))
		}
		val result = query.fetchResults()
		return result.results
	}
}
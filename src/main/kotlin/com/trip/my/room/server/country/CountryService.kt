package com.trip.my.room.server.country

import com.trip.my.room.server.story.domain.repository.StoryRepository
import com.trip.my.room.server.user.UserRepository
import org.springframework.stereotype.Service
import java.util.*
import kotlin.streams.toList

val COUNTRY_TYPE_LIST: List<String> = listOf(
		"KOREA", "USA", "UK", "TAIWAN",
		"RUSSIA", "MACAU", "VIETNAM", "SPAIN",
		"SINGAPORE", "ITALY", "JAPAN", "CHINA",
		"THAILAND", "FRANCE", "AUSTRALIA", "HONGKONG"
)

@Service
class CountryService(
		private val countryRepository: CountryRepository,
		private val storyRepository: StoryRepository,
		private val userRepository: UserRepository,
		private val countryMapper: CountryMapper
) {
	
	fun getPlaceWithStoryCount(userId: UUID): List<CountryStoryCountResponseDto> {
		val allOfCountryEntity = COUNTRY_TYPE_LIST.stream()
				.map { countryType -> countryRepository.findAllByType(countryType) }
				.flatMap { countryEntityList -> countryEntityList.stream() }
				.toList()
		
		return getCountryStoryCountDtoWithCount(userId, allOfCountryEntity)
	}
	
	private fun getCountryStoryCountDtoWithCount(
			userId: UUID,
			allOfCountryEntity: List<CountryEntity>
	): List<CountryStoryCountResponseDto> {
		val countryStoryCountResponseDtoList = allOfCountryEntity.stream()
				.map { countryEntity ->
					CountryStoryCountResponseDto(
							countryEntity.id,
							countryEntity.name,
							countryEntity.type,
							storyRepository.countByUserIdAndCountryType(userId, countryEntity.type),
							countryEntity.flagImageUrl,
							countryEntity.letterImageUrl
					)
				}
				.toList()
				.toMutableList()
		countryStoryCountResponseDtoList.add(getCountryStoryCountAboutOtherType(userId))
		
		return countryStoryCountResponseDtoList
	}
	
	private fun getCountryStoryCountAboutOtherType(userId: UUID): CountryStoryCountResponseDto {
		val otherCountryEntity = countryRepository.findByNameAndType("기타", "OTHER")
		
		val foundUserEntity = userRepository.findById(userId).get()
		val otherCountryCount = countryRepository.countByUserAndType(foundUserEntity, "OTHER")
		
		return CountryStoryCountResponseDto(
				otherCountryEntity.id,
				otherCountryEntity.name,
				otherCountryEntity.type,
				otherCountryCount,
				otherCountryEntity.flagImageUrl,
				otherCountryEntity.letterImageUrl
		)
	}
	
	fun getCountriesByUserId(userId: UUID, others: Boolean): List<CountryDto.CountryOut> {
		return countryRepository.getCountriesByUserId(userId, others).stream()
				.map { country -> countryMapper.toDto(country) }
				.toList()
	}
	
	fun createNewCountry(countryEntity: CountryEntity): CountryEntity {
		return countryRepository.save(countryEntity)
	}
	
	fun createNewOtherCountry(name: String?, userId: UUID): CountryEntity {
		val foundUserEntity = userRepository.findById(userId).get()
		
		val countryEntity = CountryEntity().apply {
			this.name = name
			this.type = "OTHER"
			this.user = foundUserEntity
		}
		
		return createNewCountry(countryEntity)
	}
	
	fun getByCountryId(countryId: UUID): CountryEntity {
		return countryRepository.findById(countryId).get()
	}
	
	fun getCountryResponseDtoById(countryId: UUID): CountryResponseDto {
		val foundCountryEntity = countryRepository.findById(countryId).get()
		return CountryResponseDto(
				foundCountryEntity.id,
				foundCountryEntity.name,
				foundCountryEntity.type,
				foundCountryEntity.flagImageUrl,
				foundCountryEntity.letterImageUrl
		)
	}
}

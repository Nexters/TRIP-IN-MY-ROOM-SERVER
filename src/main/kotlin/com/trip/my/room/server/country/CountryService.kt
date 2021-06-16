package com.trip.my.room.server.country

import com.trip.my.room.server.adapter.out.persistence.jpa.entity.StoryRepository
import com.trip.my.room.server.user.UserRepository
import org.springframework.stereotype.Service
import java.util.*
import kotlin.streams.toList

@Service
class CountryService(
    private val countryRepository: CountryRepository,
    private val storyRepository: StoryRepository,
    private val userRepository: UserRepository,
    private val countryMapper: CountryMapper
) {
    val COUNTRY_TYPE_LIST: List<String> = listOf(
        "AU", "CH", "FR", "HO", "IT", "JP", "KO", "MA", "RU", "SIN", "SP", "TAI", "THAI", "UK", "USA", "VI",
    )

    fun searchByCountryName(userId: UUID, countryName: String): List<CountryResponseDto> {
        val totalSearchedCountries = mutableListOf<CountryEntity>()

        // Other를 제외한 나라 검색
        val searchedCountriesNotOtherType =
            countryRepository.findByNameContainingAndTypeIsNot(countryName, "OTHER")
        totalSearchedCountries.addAll(searchedCountriesNotOtherType)

        // 사용자 등록 나라 검색
        val foundUserEntity =
            userRepository.findById(userId).orElseThrow { throw NoSuchElementException("해당 하는 user 정보가 없습니다.") }
        val searchedPersonalCountiesList =
            countryRepository.findByNameContainingAndUser(countryName, foundUserEntity)
        totalSearchedCountries.addAll(searchedPersonalCountiesList)

        return totalSearchedCountries.stream()
            .map { countryEntity -> convertDto(countryEntity) }
            .toList()
    }

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
                    countryEntity.mainFood,
                    storyRepository.countByUserIdAndCountryType(userId, countryEntity.type),
                    countryEntity.flagImageUrl,
                    countryEntity.albumStickerImageUrl,
                    countryEntity.stampImageUrl
                )
            }
            .toList()
            .toMutableList()
        countryStoryCountResponseDtoList.add(getCountryStoryCountAboutOtherType(userId))

        return countryStoryCountResponseDtoList
    }

    private fun getCountryStoryCountAboutOtherType(userId: UUID): CountryStoryCountResponseDto {
        val otherCountryEntity = countryRepository.findByNameAndType("기타", "OTHER")

        val foundUserEntity = userRepository.findById(userId)
            .orElseThrow { throw NoSuchElementException("해당 하는 user 정보가 없습니다.") }
        val otherCountryCount = countryRepository.countByUserAndType(foundUserEntity, "OTHER")

        return CountryStoryCountResponseDto(
            otherCountryEntity.id,
            otherCountryEntity.name,
            otherCountryEntity.type,
            otherCountryEntity.mainFood,
            otherCountryCount,
            otherCountryEntity.flagImageUrl,
            otherCountryEntity.albumStickerImageUrl,
            otherCountryEntity.stampImageUrl
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
        val foundUserEntity = userRepository.findById(userId)
            .orElseThrow { throw NoSuchElementException("해당 하는 user 정보가 없습니다.") }

        val countryEntity = CountryEntity().apply {
            this.name = name
            this.type = "OTHER"
            this.user = foundUserEntity
        }

        return createNewCountry(countryEntity)
    }

    fun getByCountryId(countryId: UUID): CountryEntity {
        return countryRepository.findById(countryId)
            .orElseThrow { throw NoSuchElementException("해당 하는 country 정보가 없습니다.") }
    }

    fun getCountryResponseDtoById(countryId: UUID): CountryResponseDto {
        val foundCountryEntity = countryRepository.findById(countryId)
            .orElseThrow { throw NoSuchElementException("해당 하는 country 정보가 없습니다.") }
        return CountryResponseDto(
            foundCountryEntity.id,
            foundCountryEntity.name,
            foundCountryEntity.type,
            foundCountryEntity.mainFood,
            foundCountryEntity.flagImageUrl,
            foundCountryEntity.albumStickerImageUrl,
            foundCountryEntity.stampImageUrl
        )
    }

    private fun convertDto(countryEntity: CountryEntity): CountryResponseDto {
        return CountryResponseDto(
            countryEntity.id,
            countryEntity.name,
            countryEntity.type,
            countryEntity.mainFood,
            countryEntity.flagImageUrl,
            countryEntity.albumStickerImageUrl,
            countryEntity.stampImageUrl
        )
    }
}

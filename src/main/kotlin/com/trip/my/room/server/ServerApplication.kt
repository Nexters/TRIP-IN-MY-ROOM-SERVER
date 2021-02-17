package com.trip.my.room.server

import com.trip.my.room.server.config.AwsS3BucketProperties
import com.trip.my.room.server.config.MyConfigurationProperties
import com.trip.my.room.server.country.CountryEntity
import com.trip.my.room.server.country.CountryRepository
import com.trip.my.room.server.place.PlaceRepository
import com.trip.my.room.server.user.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.event.EventListener
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing
@SpringBootApplication
@EnableConfigurationProperties(MyConfigurationProperties::class)
class ServerApplication {
	
	@Autowired
	private val userRepository: UserRepository? = null
	
	@Autowired
	private val countryRepository: CountryRepository? = null
	
	@Autowired
	private val placeRepository: PlaceRepository? = null
	
	@Autowired
	private val awsS3BucketProperties: AwsS3BucketProperties? = null
	
	@EventListener(ApplicationReadyEvent::class)
	fun afterStartUp() {
		val baseUrl = "https://${awsS3BucketProperties!!.bucketName}.s3.ap-northeast-2.amazonaws.com"
		val countries = mutableListOf<List<String>>(
				listOf("Australia", "오스트리아", "AU"),
				listOf("China", "중국", "CH"),
				listOf("France", "프랑스", "FR"),
				listOf("Hongkong", "홍콩", "HO"),
				listOf("Italy", "이탈리아", "IT"),
				listOf("Japan", "일본", "JP"),
				listOf("Korea", "한국", "KO"),
				listOf("Macau", "마카오", "MA"),
				listOf("Russia", "러시아", "RU"),
				listOf("Singapore", "싱가포르", "SIN"),
				listOf("Spain", "스페인", "SP"),
				listOf("Taiwan", "대만", "TAI"),
				listOf("Thailand", "태국", "THAI"),
				listOf("UK", "영국", "UK"),
				listOf("USA", "미국", "USA"),
				listOf("Vietnam", "베트남", "VI"))
		
		for (country in countries) {
			// 존재하지 않을 경우만 추가
			if (!countryRepository!!.findByName(country[1]).isPresent) {
				var newCountry = CountryEntity().apply {
					this.name = country[1]
					this.type = country[2]
					this.flagImageUrl = "${baseUrl}/flags/${country[0]}.svg"
					this.letterImageUrl = "${baseUrl}/main/sticker_${country[0].toLowerCase()}.svg"
				}
				countryRepository!!.save(newCountry)
			}
		}
	}
	
	companion object {
		@JvmStatic
		fun main(args: Array<String>) {
			runApplication<ServerApplication>(*args)
		}
	}
}

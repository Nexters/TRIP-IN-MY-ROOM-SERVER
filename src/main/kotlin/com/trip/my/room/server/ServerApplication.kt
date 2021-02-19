package com.trip.my.room.server

import com.trip.my.room.server.config.AwsS3BucketProperties
import com.trip.my.room.server.config.MyKakaoConfigurationProperties
import com.trip.my.room.server.config.MyNaverConfigurationProperties
import com.trip.my.room.server.country.CountryEntity
import com.trip.my.room.server.country.CountryRepository
import com.trip.my.room.server.place.PlaceRepository
import com.trip.my.room.server.user.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.event.EventListener
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing
@SpringBootApplication
@EnableConfigurationProperties(MyKakaoConfigurationProperties::class)
class ServerApplication {
	
	@Autowired
	private val userRepository: UserRepository? = null
	
	@Autowired
	private val countryRepository: CountryRepository? = null
	
	@Autowired
	private val placeRepository: PlaceRepository? = null
	
	@Autowired
	private val awsS3BucketProperties: AwsS3BucketProperties? = null
	
	@Bean
	fun myNaverConfigurationProperties(): MyNaverConfigurationProperties {
		return MyNaverConfigurationProperties()
	}
	
	@EventListener(ApplicationReadyEvent::class)
	fun afterStartUp() {
		val baseUrl = "https://${awsS3BucketProperties!!.bucketName}.s3.ap-northeast-2.amazonaws.com"
		val countries = mutableListOf(
				listOf("Australia", "오스트리아", "AU", "에그타르트, 딤심, 완탕면, 우육면, 계란와플"),
				listOf("China", "중국", "CH", "마라탕, 마라샹궈, 마파두부, 짜장면, 짬뽕"),
				listOf("France", "프랑스", "FR", "마카롱, 크로와상, 바게트, 라따뚜이"),
				listOf("Hongkong", "홍콩", "HO", "에그타르트, 딤심, 완탕면, 우육면, 계란와플"),
				listOf("Italy", "이탈리아", "IT", "피자, 파스타, 티라미수, 젤라또, 라자냐"),
				listOf("Japan", "일본", "JP", "초밥, 라멘, 오코노미야키, 타코야키"),
				listOf("Korea", "한국", "KO", "곱창, 불고기, 비빔밥, 삼겹살, 떡볶이"),
				listOf("Macau", "마카오", "MA", "버블티, 에그타르트, 우유푸딩, 아몬드쿠키"),
				listOf("Russia", "러시아", "RU", "보드카, 샤슬릭, 새우, 킹크랩"),
				listOf("Singapore", "싱가포르", "SIN", "카야토스트, 칠리크랩, 락사, 당근케이크"),
				listOf("Spain", "스페인", "SP", "감바스, 빠에야, 뱅쇼, 이베리코, 하몽"),
				listOf("Taiwan", "대만", "TAI", "망고빙수, 밀크티, 지파이, 펑리수"),
				listOf("Thailand", "태국", "THAI", "똠양꿍, 팟타이, 솜땀, 푸팟퐁커리"),
				listOf("UK", "영국", "UK", "홍차, 브런치, 피시앤칩스"),
				listOf("USA", "미국", "USA", "햄버거, 핫도그, 맥앤치즈, 바베큐"),
				listOf("Vietnam", "베트남", "VI", "쌀국수, 반미, 분짜, 반쎄오, 스프링롤"),
				listOf("etc", "기타", "OTHER", "기타"))
		
		for (country in countries) {
			// 존재하지 않을 경우만 추가
			if (!countryRepository!!.findByName(country[1]).isPresent) {
				var newCountry = CountryEntity().apply {
					this.name = country[1]
					this.type = country[2]
					this.flagImageUrl = "${baseUrl}/flags/${country[0]}.svg"
					this.albumStickerImageUrl = "${baseUrl}/stamps/album/album_${country[0].toLowerCase()}.svg"
					this.stampImageUrl = "${baseUrl}/stamps/main/sticker_${country[0].toLowerCase()}.svg"
					this.mainFood = country[3]
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

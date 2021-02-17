package com.trip.my.room.server

import com.trip.my.room.server.config.MyConfigurationProperties
import com.trip.my.room.server.country.CountryEntity
import com.trip.my.room.server.country.CountryRepository
import com.trip.my.room.server.place.PlaceEntity
import com.trip.my.room.server.place.PlaceRepository
import com.trip.my.room.server.user.UserEntity
import com.trip.my.room.server.user.UserRepository
import com.trip.my.room.server.user.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.event.EventListener
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import kotlin.random.Random

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

//    @EventListener(ApplicationReadyEvent::class)
//    fun afterStartUp() {
//        // dummy Value
//        var newUser = UserEntity().apply {
//            this.name = "test"
//        }
//        val userEntity = userRepository!!.save(newUser)
//        println("user=$userEntity")
//
//        val listOfPairCountryAndName = listOf(Pair("KOREA", "한국"), Pair("OTHER", "기타"))
//        // 10개 정도의 나라를 생성
//        for (idx in 0..1) {
//            var newCountry = CountryEntity().apply {
//                this.type = listOfPairCountryAndName[idx].first
//                this.name = listOfPairCountryAndName[idx].second
//            }
//            val countryEntity = countryRepository!!.save(newCountry)
//            println("countryId=${countryEntity.id}")
//
//            var newPlace = PlaceEntity().apply {
//                this.name = "place${idx}"
//                this.longitude = Random(idx).nextDouble()
//                this.latitude = Random(idx).nextDouble()
//            }
//
//            val placeEntity = placeRepository!!.save(newPlace)
//            println("placeId=$placeEntity.id")
//        }
//    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            runApplication<ServerApplication>(*args)
        }
    }
}

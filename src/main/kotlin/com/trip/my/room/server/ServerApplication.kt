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
    private val userService: UserService? = null

//    @EventListener(ApplicationReadyEvent::class)
//    fun afterStartUp() {
//        // dummy Value
//        var newUser = UserEntity().apply {
//            this.name = "test"
//        }
//        userRepository!!.save(newUser)
//
//        // 10개 정도의 나라를 생성
//        for (idx in 1..11) {
//            var newCountry = CountryEntity().apply {
//                this.name = "country${idx}"
//            }
//            countryRepository!!.save(newCountry)
//
//            var newPlace = PlaceEntity().apply {
//                this.country = newCountry
//                this.name = "place${idx}"
//                this.country = newCountry
//            }
//
//            if (idx % 3 == 0) {
//                newPlace.user = newUser
//                newPlace.customized = true
//            }
//            placeRepository!!.save(newPlace)
//        }
//    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            runApplication<ServerApplication>(*args)
        }
    }
}

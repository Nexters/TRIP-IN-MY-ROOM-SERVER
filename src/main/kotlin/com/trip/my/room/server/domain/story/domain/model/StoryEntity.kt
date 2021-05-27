package com.trip.my.room.server.domain.story.domain.model

import com.trip.my.room.server.common.entity.TimeEntity
import com.trip.my.room.server.country.CountryEntity
import com.trip.my.room.server.place.PlaceEntity
import org.hibernate.annotations.GenericGenerator
import org.springframework.data.annotation.LastModifiedDate
import java.time.Instant
import java.util.*
import javax.persistence.*

@Entity(name = "story")
class StoryEntity(
    title: String,
    date: Instant,
    memo: String,
    userId: UUID,
    place: PlaceEntity,
    country: CountryEntity,
    countryType: String?
) : TimeEntity() {

    @field:Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    var id: UUID? = null

    var title: String? = title

    var date: Instant? = date

    var memo: String? = memo

    @field: LastModifiedDate
    var updatedAt: Instant? = null

    @Column(columnDefinition = "BINARY(16)")
    var userId: UUID? = userId

    @field: ManyToOne
    @field: JoinColumn(name = "place_id", nullable = true, columnDefinition = "BINARY(16)")
    var place: PlaceEntity? = place

    @field: ManyToOne
    @field: JoinColumn(name = "conuntry_id", nullable = true, columnDefinition = "BINARY(16)")
    var country: CountryEntity? = country

    var countryType: String? = countryType

    fun update(title: String, date: Instant, memo: String) {
        this.title = title
        this.date = date
        this.memo = memo
    }

    fun updatePlace(placeEntity: PlaceEntity) {
        this.place = placeEntity
    }

    fun updateCountry(countryEntity: CountryEntity) {
        this.country = countryEntity
        this.countryType = countryEntity.type
    }
}

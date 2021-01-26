package com.trip.my.room.server.story

import com.trip.my.room.server.common.entity.TimeEntity
import com.trip.my.room.server.place.RelUserPlaceEntity
import org.springframework.data.annotation.LastModifiedDate
import java.time.Instant
import java.util.*
import javax.persistence.*

@Entity(name = "story")
class StoryEntity : TimeEntity(){
	
	@field:Id
	@field:GeneratedValue(strategy = GenerationType.AUTO)
	var id: UUID ?= null
	
	@field: ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "rel_user_place_id")
	var userPlace : RelUserPlaceEntity?= null
	
	var title: String ?= null
	
	var date: Instant ?= null
	
	var memo: String ?= null
	
	@field: LastModifiedDate
	var updatedAt : Instant ?= null
}
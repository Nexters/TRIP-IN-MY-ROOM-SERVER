package com.trip.my.room.server.picture

import com.trip.my.room.server.common.enum.PictureOrder
import com.trip.my.room.server.story.domain.model.StoryEntity
import java.util.*
import javax.persistence.*

@Entity(name= "picture")
class PictureEntity {
	
	@field:Id
	@field:GeneratedValue(strategy = GenerationType.AUTO)
	var id : UUID ?= null
	
	@Enumerated(EnumType.STRING)
	var pictureOrder: PictureOrder ?= null
	
	var url: String ?= null
	
	var fileName: String ?= null
	
	@field: ManyToOne(fetch = FetchType.LAZY)
	@field: JoinColumn(name = "story_id")
	var story: StoryEntity?= null
}

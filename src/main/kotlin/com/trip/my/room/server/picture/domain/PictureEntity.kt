package com.trip.my.room.server.picture.domain

import com.trip.my.room.server.common.enum.PictureOrder
import com.trip.my.room.server.story.domain.model.StoryEntity
import java.util.*
import javax.persistence.*

@Entity(name = "picture")
class PictureEntity(
    pictureOrder: PictureOrder?,
    url: String,
    fileName: String,
    story: StoryEntity
) {

    @field:Id
    @field:GeneratedValue(strategy = GenerationType.AUTO)
    var id: UUID? = null

    @Enumerated(EnumType.STRING)
    var pictureOrder: PictureOrder? = pictureOrder

    var url: String? = url

    var fileName: String? = fileName

    @field: ManyToOne(fetch = FetchType.LAZY)
    @field: JoinColumn(name = "story_id")
    var story: StoryEntity? = story
}

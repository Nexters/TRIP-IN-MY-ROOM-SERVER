package com.trip.my.room.server.adapter.out.persistence.jpa.entity

import com.trip.my.room.server.common.enum.PictureOrder
import org.hibernate.annotations.GenericGenerator
import java.util.*
import javax.persistence.*

@Entity(name = "picture")
class PictureEntity(
    pictureOrder: PictureOrder?,
    url: String,
    fileName: String,
    storageKey: String,
    story: StoryEntity
) {

    @field:Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    var id: UUID? = null

    @Enumerated(EnumType.STRING)
    var pictureOrder: PictureOrder? = pictureOrder

    var url: String? = url

    var fileName: String? = fileName

    var storageKey: String? = storageKey

    @field: ManyToOne(fetch = FetchType.LAZY)
    @field: JoinColumn(name = "story_id", columnDefinition = "BINARY(16)")
    var story: StoryEntity? = story
}

package com.trip.my.room.server.common.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.Instant
import javax.persistence.Column
import javax.persistence.EntityListeners
import javax.persistence.MappedSuperclass

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class TimeEntity {
	@CreatedDate
	@Column(nullable = false, updatable = false)
	var createdAt: Instant? = null
}
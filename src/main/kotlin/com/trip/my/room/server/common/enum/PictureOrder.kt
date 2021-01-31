package com.trip.my.room.server.common.enum

enum class PictureOrder(private val value: Int) {
	one(1), two(2), three(3), four(4), five(5);
	
	fun getValue() = value
}

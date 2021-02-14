package com.trip.my.room.server.common.enum

enum class PictureOrder(private val value: Int) {
	ONE(1), TWO(2), THREE(3), FOUR(4), FIVE(5);

	fun getValue(): Int {
		return value
	}

	companion object {
		fun fromInt(value: Int) = values().firstOrNull() { it.value == value }
	}
}

package com.trip.my.room.server.picture

import com.trip.my.room.server.common.enum.PictureOrder
import org.springframework.web.multipart.MultipartFile

class PictureFile(val picture: MultipartFile, val order: PictureOrder?) {
}

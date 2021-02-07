package com.trip.my.room.server.config

import com.trip.my.room.server.common.upload.UploadProperties
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*

@Component
data class AwsS3BucketProperties(
    @Value("\${cloud.aws.s3.bucket}")
    var bucketName: String = "",

    @Value("\${amazon.s3.directoryPath}")
    var directoryPath: String = ""
) : UploadProperties {

    override fun getBasePath(): String {
        return directoryPath
    }

    override fun getBasePathWithUserId(userId: UUID): String {
        return "$directoryPath/$userId"
    }

}

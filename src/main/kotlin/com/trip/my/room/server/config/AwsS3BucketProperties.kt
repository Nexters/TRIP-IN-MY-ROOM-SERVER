package com.trip.my.room.server.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
data class AwsS3BucketProperties(
    @Value("\${cloud.aws.s3.bucket}")
    var bucketName: String = "",

    @Value("\${amazon.s3.directoryPath}")
    var directoryPath: String = ""
) {}

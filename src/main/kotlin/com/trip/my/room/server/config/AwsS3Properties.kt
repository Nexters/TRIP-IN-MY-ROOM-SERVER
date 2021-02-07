package com.trip.my.room.server.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
data class AwsS3Properties(
    @Value("\${cloud.aws.credentials.accessKey}")
    var accessKey: String = "",

    @Value("\${cloud.aws.credentials.secretKey}")
    var secretKey: String = "",

    @Value("\${cloud.aws.region.static}")
    var region: String = "",
)

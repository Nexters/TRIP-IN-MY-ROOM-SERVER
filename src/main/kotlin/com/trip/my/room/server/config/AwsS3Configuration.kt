package com.trip.my.room.server.config

import com.amazonaws.auth.AWSCredentials
import com.amazonaws.services.s3.AmazonS3
import org.springframework.context.annotation.Configuration

@Configuration
interface AwsS3Configuration {

    fun amazonS3(awsCredentials: AWSCredentials): AmazonS3

}

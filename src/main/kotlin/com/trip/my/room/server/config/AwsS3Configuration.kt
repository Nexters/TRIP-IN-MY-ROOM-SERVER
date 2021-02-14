package com.trip.my.room.server.config

import com.amazonaws.auth.AWSCredentials
import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.regions.Regions
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AwsS3Configuration(private val awsS3Properties: AwsS3Properties) {

    @Bean
    fun awsCredentials(): AWSCredentials {
        return BasicAWSCredentials(awsS3Properties.accessKey, awsS3Properties.secretKey)
    }

    @Bean
    fun amazonS3(awsCredentials: AWSCredentials): AmazonS3 {
        return AmazonS3ClientBuilder.standard()
            .withCredentials(AWSStaticCredentialsProvider(awsCredentials))
            .withRegion(Regions.fromName(awsS3Properties.region))
            .build()
    }
}

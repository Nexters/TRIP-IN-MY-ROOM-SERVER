package com.trip.my.room.server.config

import com.amazonaws.auth.AWSCredentials
import com.amazonaws.auth.InstanceProfileCredentialsProvider
import com.amazonaws.regions.Regions
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Profile("prod")
@Configuration
class AwsS3ConfigurationEc2Instance(private val awsS3Properties: AwsS3Properties) : AwsS3Configuration {

    @Bean
    override fun amazonS3(awsCredentials: AWSCredentials): AmazonS3 {
        val instanceProfileCredentialsProvider = InstanceProfileCredentialsProvider(true)

        return AmazonS3ClientBuilder.standard()
            .withCredentials(instanceProfileCredentialsProvider)
            .withRegion(Regions.fromName(awsS3Properties.region))
            .build()
    }
}

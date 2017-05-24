package com.gp.access.impl

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsyncClient
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

@Component
open class AmazonDynamoDBAsyncClientProvider {
    @Bean open fun amazonDynamoDBAsyncClient(): AmazonDynamoDBAsyncClient {
        val amazonDynamoDBAsyncClient = AmazonDynamoDBAsyncClient(DefaultAWSCredentialsProviderChain())
        amazonDynamoDBAsyncClient.setEndpoint("http://localhost:8000")
        return amazonDynamoDBAsyncClient
    }
}
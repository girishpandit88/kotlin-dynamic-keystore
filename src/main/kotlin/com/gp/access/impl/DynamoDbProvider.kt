package com.gp.access.impl

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsyncClient
import com.amazonaws.services.dynamodbv2.document.DynamoDB
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component


@Component("dynamoDb")
open class DynamoDbProvider @Autowired constructor (val dynamoDbClient : AmazonDynamoDBAsyncClient) {
    @Bean open fun dynamoDbProvider() = DynamoDB(this.dynamoDbClient)
}
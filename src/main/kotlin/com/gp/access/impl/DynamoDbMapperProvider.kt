package com.gp.access.impl

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsyncClient
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

@Component("dynamoDbMapper")
open class DynamoDbMapperProvider @Autowired constructor(val dynamoDbClient: AmazonDynamoDBAsyncClient) {
    @Bean
    open fun DynamoDbMapperProvider() =
            DynamoDBMapper(dynamoDbClient,
                    DynamoDBMapperConfig(DynamoDBMapperConfig.TableNameOverride.withTableNamePrefix("")))
}


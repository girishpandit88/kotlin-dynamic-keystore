package com.gp.access.impl

import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsync
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsyncClientBuilder
import com.gp.access.model.DynamoDBConfig
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

@Component
open class AmazonDynamoDBAsyncClientProvider(val dynamoDBConfig: DynamoDBConfig) {
	val logger = LoggerFactory.getLogger(AmazonDynamoDBAsyncClientProvider::class.java)!!
	@Bean open fun getAmazonDBAsync(): AmazonDynamoDBAsync {

		val amazonDynamoDBAsyncClient = AmazonDynamoDBAsyncClientBuilder
				.standard()
				.withEndpointConfiguration(EndpointConfiguration(dynamoDBConfig.endpoint,null))
				.build()
		logger.debug("Listing all tables: ${amazonDynamoDBAsyncClient.listTables().tableNames}")
		return amazonDynamoDBAsyncClient
	}
}


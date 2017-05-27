package com.gp.access.impl

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsync
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig
import com.gp.access.model.DynamoDBConfig
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

@Component
open class DynamoDbMapperProvider(val dynamoDBAsync: AmazonDynamoDBAsync, val dynamoDBMConfig: DynamoDBConfig) {

	@Bean open fun DynamoDbMapperProvider() =
			DynamoDBMapper(dynamoDBAsync,
					DynamoDBMapperConfig
							.builder()
							.withTableNameOverride(DynamoDBMapperConfig.TableNameOverride.withTableNamePrefix(
									dynamoDBMConfig.tablePrefix))
							.build()
						  )
}


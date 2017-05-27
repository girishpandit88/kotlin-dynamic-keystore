package com.gp.access.impl

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList
import com.gp.access.KeystoreAccess
import com.gp.access.model.KeystoreDao
import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*
import java.util.stream.Collectors.toMap

@Service
open class KeystoreAccessImpl @Autowired constructor(val dynamoDbMapper: DynamoDBMapper) : KeystoreAccess {
	val logger = LoggerFactory.getLogger(KeystoreAccessImpl::class.java)
	override fun getConfig(hashKey: String, rangeKey: String) =
			Optional.of(dynamoDbMapper.load(KeystoreDao::class.java, hashKey, rangeKey).value)

	override fun setConfig(keyStores: List<KeystoreDao>) {
		for (failedBatch in dynamoDbMapper.batchSave(keyStores)) {
			logger.info(failedBatch.exception.message)
		}
	}

	override fun getConfigs(hashKey: String): Optional<Config> {
		val paginatedQueryList = dynamoDbMapper
				.query(KeystoreDao::class.java,
						DynamoDBQueryExpression<KeystoreDao>().withHashKeyValues(KeystoreDao(hashKey, "", "")))
		return extractConfigs(paginatedQueryList)
	}

	private fun extractConfigs(paginatedQueryList: PaginatedQueryList<KeystoreDao>?): Optional<Config> {
		if (paginatedQueryList?.isEmpty() ?: true) return Optional.empty()
		val result = paginatedQueryList
				?.stream()
				?.collect(toMap(KeystoreDao::getKey, KeystoreDao::getValue))
		result?.forEach { logger.info(it.key,it.value) }
		return Optional.of(ConfigFactory.parseMap(result))

	}

	override fun deleteConfig(hashKey: String, rangeKey: String) = dynamoDbMapper.delete(KeystoreDao(hashKey,
			rangeKey,
			""));
}
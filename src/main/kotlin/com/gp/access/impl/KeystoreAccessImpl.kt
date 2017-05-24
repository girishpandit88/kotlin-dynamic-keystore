package com.gp.access.impl

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList
import com.gp.access.KeystoreAccess
import com.gp.access.model.KeystoreDao
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*
import java.util.stream.Collectors

@Service
open class KeystoreAccessImpl @Autowired constructor(val dynamoDbMapper: DynamoDBMapper) : KeystoreAccess {
    override fun getConfig(hashKey: String, rangeKey: String) =
            Optional.of(dynamoDbMapper.load(KeystoreDao::class.java, hashKey, rangeKey).value)

    override fun setConfig(keyStores: List<KeystoreDao>) {
        for (failedBatch in dynamoDbMapper.batchSave(keyStores)) {
            println(message = failedBatch.exception.message)
        }
    }

    override fun getConfigs(hashKey: String) : Optional<String> {
        val paginatedQueryList = dynamoDbMapper
                .query(KeystoreDao::class.java,
                        DynamoDBQueryExpression<KeystoreDao>().withHashKeyValues(KeystoreDao(hashKey, "", "")))
        return extractConfigs(paginatedQueryList)
    }

    private fun extractConfigs(paginatedQueryList: PaginatedQueryList<KeystoreDao>?) : Optional<String> {
        if(paginatedQueryList?.isEmpty()?:true) return Optional.empty()

        return Optional.of(
                paginatedQueryList
                        ?.stream()
                        ?.collect(Collectors.toMap(KeystoreDao::getKey, KeystoreDao::getValue))
                        .toString())

    }

    override fun deleteConfig(hashKey: String, rangeKey: String) = dynamoDbMapper.delete(KeystoreDao(hashKey,rangeKey,""));
}
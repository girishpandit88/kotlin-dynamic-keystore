package com.gp.access.impl

import com.gp.access.KeystoreAccess
import com.gp.access.model.DynamoDBConfig
import com.gp.access.model.KeystoreDao
import com.typesafe.config.Config
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner


@RunWith(SpringJUnit4ClassRunner::class)
@ContextConfiguration(classes = arrayOf(KeystoreAccessImplTest::class))
@TestPropertySource(properties = arrayOf("dynamo.endpoint=http://localhost:8000", "dynamo.tablePrefix="))
class KeystoreAccessImplTest : IntegrationTest {
	lateinit var keystoreAccess : KeystoreAccess
	@Autowired lateinit var env : Environment
	@Before
	fun setup() {
		val dynamoDbConfig = DynamoDBConfig(env.getProperty("dynamo.endpoint"),env.getProperty("dynamo.tablePrefix"))
		val client = AmazonDynamoDBAsyncClientProvider(dynamoDbConfig).getAmazonDBAsync()
		keystoreAccess = KeystoreAccessImpl(DynamoDbMapperProvider(client,dynamoDbConfig).DynamoDbMapperProvider())
	}

	@Test
	fun setAndGetConfig() {
		keystoreAccess.setConfig(arrayListOf(KeystoreDao("test", "user", "xyz")))
		assert(keystoreAccess.getConfig("test","user").isPresent)
		assertEquals("xyz",keystoreAccess.getConfig("test","user").get())
	}

	@Test
	fun setAndGetConfigs() {
		keystoreAccess.setConfig(arrayListOf(KeystoreDao("test", "user", "xyz")))
		assert(keystoreAccess.getConfigs("test").isPresent)
		assert(keystoreAccess.getConfigs("test").get() is Config)
		assertEquals(keystoreAccess.getConfigs("test").get().getString("user"), "xyz")
	}

	@Test
	fun setAndAskInvalidConfig(){
		keystoreAccess.setConfig(arrayListOf(KeystoreDao("test1", "user", "xyz")))
		assertTrue(!keystoreAccess.getConfigs("testi").isPresent)
	}

	@Test
	fun deleteConfig() {
		keystoreAccess.setConfig(arrayListOf(KeystoreDao("testa", "user", "xyz")))
		keystoreAccess.deleteConfig("testa","user")
		assertTrue(!keystoreAccess.getConfigs("testa").isPresent)
	}

}


interface IntegrationTest {
	fun getPort() = System.getProperty("dynamodb.port")
}

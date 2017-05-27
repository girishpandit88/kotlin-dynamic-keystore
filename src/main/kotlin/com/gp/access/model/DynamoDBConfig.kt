package com.gp.access.model

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties("dynamo")
open class DynamoDBConfig(var endpoint: String? = null, var tablePrefix: String? = null)
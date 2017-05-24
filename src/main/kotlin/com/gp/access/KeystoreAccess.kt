package com.gp.access

import com.gp.access.model.KeystoreDao
import java.util.*

interface KeystoreAccess {
    fun getConfig(hashKey: String, rangeKey: String): Optional<String>
    fun setConfig(keyStores: List<KeystoreDao>): Unit
    fun getConfigs(hashKey: String): Optional<String>
    fun deleteConfig(hashKey: String, rangeKey: String): Unit
}
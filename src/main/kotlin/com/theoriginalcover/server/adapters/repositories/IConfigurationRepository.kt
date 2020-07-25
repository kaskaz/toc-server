package com.theoriginalcover.server.adapters.repositories

interface IConfigurationRepository {
    fun getByKey(key: String): String?
    fun save(key: String, value: String)
}

package com.theoriginalcover.server.adapters.repositories

interface IConfigurationRepository {
    fun get(config: String): String
    fun save(config: String, value: String)
}

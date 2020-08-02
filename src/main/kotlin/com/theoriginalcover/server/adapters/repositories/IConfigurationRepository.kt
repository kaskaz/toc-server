package com.theoriginalcover.server.adapters.repositories

import javax.inject.Singleton

@Singleton
interface IConfigurationRepository {
    fun get(config: String): String
    fun save(config: String, value: String)
}

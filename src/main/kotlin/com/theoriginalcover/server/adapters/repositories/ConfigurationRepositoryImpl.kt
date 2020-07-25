package com.theoriginalcover.server.adapters.repositories

import javax.inject.Singleton

@Singleton
class ConfigurationRepositoryImpl : IConfigurationRepository {
    override fun getByKey(key: String): String {
        TODO("Not yet implemented")
    }

    override fun save(key: String, value: String) {
        TODO("Not yet implemented")
    }
}

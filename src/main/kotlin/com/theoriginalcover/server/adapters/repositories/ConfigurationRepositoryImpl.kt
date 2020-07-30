package com.theoriginalcover.server.adapters.repositories

import java.util.*
import javax.inject.Singleton

@Singleton
class ConfigurationRepositoryImpl : FirebaseRepository(), IConfigurationRepository {

    companion object {
        private const val COLLECTION = "configuration"
    }

    override fun get(config: String): String {
        val sectionAndKey = getSectionKey(config)
        return getDatabase()
            .collection(COLLECTION)
            .document(sectionAndKey.first)
            .get().get().getString(sectionAndKey.second) ?: ""
    }

    override fun save(config: String, value: String) {
        val sectionAndKey = getSectionKey(config)
        getDatabase()
            .collection(COLLECTION)
            .document(sectionAndKey.first)
            .update(Collections.singletonMap(sectionAndKey.second, value) as Map<String, String>)
            .get()
    }

    private fun getSectionKey(config: String): Pair<String, String> {
        return config.split(":", limit = 2).zipWithNext().first()
    }
}

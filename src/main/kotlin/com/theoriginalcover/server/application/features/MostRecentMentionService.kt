package com.theoriginalcover.server.application.features

import com.theoriginalcover.server.adapters.repositories.IConfigurationRepository
import com.theoriginalcover.server.domain.ConfigurationKeys
import com.theoriginalcover.server.domain.Mention
import javax.inject.Singleton

@Singleton
class MostRecentMentionService(private val configurationRepository: IConfigurationRepository) {
    fun store(mentions: List<Mention>?) {
        if (mentions.isNullOrEmpty()) return

        val maxId = mentions.map { it.id!! }.max() ?: return

        val lastProcessedMention = configurationRepository.get(ConfigurationKeys.LAST_PROCESSED_MENTION)

        if (lastProcessedMention.isBlank() || maxId > lastProcessedMention.toLong())
            configurationRepository.save(ConfigurationKeys.LAST_PROCESSED_MENTION, maxId.toString())
    }

    fun get(): Long? {
        return configurationRepository.get(ConfigurationKeys.LAST_PROCESSED_MENTION).toLongOrNull()
    }
}

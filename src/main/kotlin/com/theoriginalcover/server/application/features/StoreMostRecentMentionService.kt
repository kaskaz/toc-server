package com.theoriginalcover.server.application.features

import com.theoriginalcover.server.adapters.repositories.IConfigurationRepository
import com.theoriginalcover.server.domain.Mention

class StoreMostRecentMentionService(private val configurationRepository: IConfigurationRepository) {

    companion object {
        const val LAST_PROCESSED_MENTION: String = "last_processed_mention"
    }

    fun store(mentions: List<Mention>?) {
        if (mentions.isNullOrEmpty()) return

        val maxId = mentions.map { it.id!! }.max() ?: return

        val lastProcessedMention = configurationRepository.getByKey(LAST_PROCESSED_MENTION)

        if (lastProcessedMention == null || maxId > lastProcessedMention.toLong())
            configurationRepository.save(LAST_PROCESSED_MENTION, maxId.toString())
    }
}

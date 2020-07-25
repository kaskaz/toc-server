package com.theoriginalcover.server.aplication.features

import com.theoriginalcover.server.adapters.repositories.IConfigurationRepository
import com.theoriginalcover.server.application.features.StoreMostRecentMentionService
import com.theoriginalcover.server.domain.Mention
import com.theoriginalcover.server.domain.MentionMotherBuilder
import io.mockk.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class StoreMostRecentMentionServiceTest {

    private val configurationRepository = mockk<IConfigurationRepository>()
    private val utStoreMostRecentMentionService = StoreMostRecentMentionService(configurationRepository)

    private val configKey = StoreMostRecentMentionService.LAST_PROCESSED_MENTION

    @Test
    @DisplayName("should not store the most recent mention when less than previous configured value")
    fun shouldNotStoreTheMostRecentMentionWhenLessThanPreviousConfiguredValue() {
        val mentions = getListOfMentions(1, 2)
        val previousConfigValue = "3"

        verifyAndDoNotSave(mentions, previousConfigValue)
    }

    @Test
    @DisplayName("should not store the most recent mention when equals previous configured value")
    fun shouldNotStoreTheMostRecentMentionWhenEqualsPreviousConfiguredValue() {
        val mentions = getListOfMentions(1, 2)
        val previousConfigValue = "2"

        verifyAndDoNotSave(mentions, previousConfigValue)
    }

    @Test
    @DisplayName("should store the most recent mention when higher than previous configuration value")
    fun shouldStoreTheMostRecentMentionWhenHigherThanPreviousConfigurationValue() {
        val mentions = getListOfMentions(2, 3)
        val mostRecentId = "3"
        val previousConfigValue = "1"

        verifyAndSave(mentions, previousConfigValue, mostRecentId)
    }

    @Test
    @DisplayName("should store the most recent mention without previous configuration value")
    fun shouldStoreTheMostRecentMentionWithoutPreviousConfigurationValue() {
        val mentions = getListOfMentions(2, 3)
        val mostRecentId = "3"
        val previousConfigValue = null

        verifyAndSave(mentions, previousConfigValue, mostRecentId)
    }

    private fun getListOfMentions(firstMentionId: Long, secondMentionId: Long): List<Mention> {
        val builder = MentionMotherBuilder()

        return listOf<Mention>(
            builder.withId(firstMentionId).build(),
            builder.withId(secondMentionId).build()
        )
    }

    private fun verifyAndSave(mentions: List<Mention>?, previousConfigValue: String?, mostRecentId: String) {

        every { configurationRepository.getByKey(configKey) } returns previousConfigValue
        justRun { configurationRepository.save(configKey, mostRecentId) }

        utStoreMostRecentMentionService.store(mentions)

        verifyOrder {
            configurationRepository.getByKey(configKey)
            configurationRepository.save(configKey, mostRecentId)
        }

        confirmVerified(configurationRepository)
    }

    private fun verifyAndDoNotSave(mentions: List<Mention>?, previousConfigValue: String?) {

        every { configurationRepository.getByKey(configKey) } returns previousConfigValue

        utStoreMostRecentMentionService.store(mentions)

        verifyOrder {
            configurationRepository.getByKey(configKey)
        }

        confirmVerified(configurationRepository)
    }
}

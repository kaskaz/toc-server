package integration.com.theoriginalcover.server.adapters.repositories

import com.theoriginalcover.server.adapters.repositories.firebase.FirebaseConfigurationRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.util.*

class FirebaseConfigurationRepositoryTest {

    @Test
    @DisplayName("should save and retrieve the same configuration from database")
    fun shouldSaveAndRetrieveTheSameConfigurationFromDatabase() {

        val section = "integration-tests"
        val key = UUID.randomUUID().toString()
        val value = UUID.randomUUID().toString()
        val config = section.plus(":").plus(key)

        val configurationRepository = FirebaseConfigurationRepository()
        configurationRepository.save(config, value)
        val result = configurationRepository.get(config)

        assertEquals(value, result)
    }
}

package com.theoriginalcover.server.aplication.twitter.likes

import com.theoriginalcover.server.adapters.rest.Favorites
import com.theoriginalcover.server.application.twitter.likes.SendLikeService
import io.mockk.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.random.Random

class SendLikeServiceTest {

    private val favorites = mockk<Favorites>()
    private val utSendLikeService = SendLikeService(favorites)

    @Test
    @DisplayName("should not send like if ID is null")
    fun shouldNotSendLikeIfIdIsNull() {
        utSendLikeService.send(null)
        confirmVerified(favorites)
    }

    @Test
    @DisplayName("should send like if ID is non-null")
    fun shouldSendLikeIfIdNonNull() {
        val id = Random.nextLong()

        justRun { favorites.create(id) }

        utSendLikeService.send(id)

        verifyOrder { favorites.create(id) }
        confirmVerified(favorites)
    }
}

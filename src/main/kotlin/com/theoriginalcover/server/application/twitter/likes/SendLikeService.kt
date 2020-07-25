package com.theoriginalcover.server.application.twitter.likes

import com.theoriginalcover.server.adapters.rest.Favorites
import org.slf4j.LoggerFactory
import javax.inject.Singleton

@Singleton
class SendLikeService(private val favorites: Favorites) {

    companion object {
        private val logger = LoggerFactory.getLogger(SendLikeService::class.java)
    }

    fun send(id: Long?) {
        if (id == null) return

        logger.info("Sending like to status with ID=$id")
        favorites.create(id)
    }
}

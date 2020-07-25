package com.theoriginalcover.server.adapters.rest

import org.slf4j.LoggerFactory
import twitter4j.TwitterException
import twitter4j.TwitterFactory

class Favorites {

    companion object {
        private val logger = LoggerFactory.getLogger(Favorites::class.java)
    }

    fun create(id: Long) {
        try {
            val status = TwitterFactory.getSingleton()
                .favorites()
                .createFavorite(id)

            if (status.isFavorited)
                logger.info("Created successfully favorite for status with ID=$id.")
            else
                logger.warn("Failed to create favorite for status with ID=$id.")
        } catch (e: TwitterException) {
            if (e.errorCode == 139)
                logger.warn("Status with ID=$id already favorited")
            else
                logger.error("Failed to create favorite for status with ID=$id.", e)
        }
    }
}

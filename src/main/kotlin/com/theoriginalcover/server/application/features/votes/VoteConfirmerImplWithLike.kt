package com.theoriginalcover.server.application.features.votes

import com.theoriginalcover.server.adapters.listeners.GetMentionsTimelineListener
import com.theoriginalcover.server.application.twitter.likes.SendLikeService
import com.theoriginalcover.server.domain.Mention
import com.theoriginalcover.server.domain.Vote
import org.slf4j.LoggerFactory
import javax.inject.Singleton

@Singleton
class VoteConfirmerImplWithLike(val sendLikeService: SendLikeService) : IVoteConfirmer {

    companion object {
        private val logger = LoggerFactory.getLogger(VoteConfirmerImplWithLike::class.java)
    }

    override fun confirm(vote: Vote, mention: Mention) {
        sendLikeService.send(mention.id)
        logger.info("vote confirmed: ${mention.id}")
    }
}

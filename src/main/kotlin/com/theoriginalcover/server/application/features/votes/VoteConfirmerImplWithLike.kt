package com.theoriginalcover.server.application.features.votes

import com.theoriginalcover.server.application.twitter.likes.SendLikeService
import com.theoriginalcover.server.domain.Mention
import com.theoriginalcover.server.domain.Vote
import javax.inject.Singleton

@Singleton
class VoteConfirmerImplWithLike(val sendLikeService: SendLikeService) : IVoteConfirmer {
    override fun confirm(vote: Vote, mention: Mention) {
        sendLikeService.send(mention.id)
    }
}

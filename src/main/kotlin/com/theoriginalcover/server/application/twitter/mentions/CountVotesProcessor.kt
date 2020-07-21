package com.theoriginalcover.server.application.twitter.mentions

import com.theoriginalcover.server.application.features.votes.VoteCounterService
import com.theoriginalcover.server.domain.Mention

class CountVotesProcessor(
    nextProcessor: AbstractChainedStatusesProcessor?,
    private val voteCounterService: VoteCounterService
) : AbstractChainedStatusesProcessor(nextProcessor) {

    override fun process(mentions: List<Mention>?) {
        voteCounterService.count(mentions)
    }
}

package com.theoriginalcover.server.application.twitter.mentions

import com.theoriginalcover.server.application.features.MostRecentMentionService
import com.theoriginalcover.server.application.features.votes.VoteCounterService
import com.theoriginalcover.server.domain.Mention
import javax.inject.Singleton

@Singleton
class GetMentionsTimelineService(
    mostRecentMentionService: MostRecentMentionService,
    voteCounterService: VoteCounterService
) {

    private val processor: AbstractChainedStatusesProcessor

    init {
        val countVotesProcessor = CountVotesProcessor(null, voteCounterService)
        processor = StoreMostRecentMentionProcessor(countVotesProcessor, mostRecentMentionService)
    }

    fun process(mentions: List<Mention>?) {
        processor.execute(mentions)
    }
}

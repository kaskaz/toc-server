package com.theoriginalcover.server.application.twitter.mentions

import com.theoriginalcover.server.application.features.StoreMostRecentMentionService
import com.theoriginalcover.server.application.features.votes.VoteCounterService
import com.theoriginalcover.server.domain.Mention
import javax.inject.Singleton

@Singleton
class GetMentionsTimelineService(
    storeMostRecentMentionService: StoreMostRecentMentionService,
    voteCounterService: VoteCounterService
) {

    private val processor: AbstractChainedStatusesProcessor

    init {
        val countVotesProcessor = CountVotesProcessor(null, voteCounterService)
        processor = StoreMostRecentMentionProcessor(countVotesProcessor, storeMostRecentMentionService)
    }

    fun process(mentions: List<Mention>?) {
        processor.execute(mentions)
    }
}

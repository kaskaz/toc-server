package com.theoriginalcover.server.application.twitter.mentions

import com.theoriginalcover.server.application.features.MostRecentMentionService
import com.theoriginalcover.server.domain.Mention

class StoreMostRecentMentionProcessor(
    nextProcessor: AbstractChainedStatusesProcessor?,
    private val mostRecentMentionService: MostRecentMentionService
) : AbstractChainedStatusesProcessor(nextProcessor) {

    override fun process(mentions: List<Mention>?) {
        mostRecentMentionService.store(mentions)
    }
}

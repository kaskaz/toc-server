package com.theoriginalcover.server.application.twitter.mentions

import com.theoriginalcover.server.application.features.StoreMostRecentMentionService
import com.theoriginalcover.server.domain.Mention

class StoreMostRecentMentionProcessor(
    nextProcessor: AbstractChainedStatusesProcessor?,
    private val storeMostRecentMentionService: StoreMostRecentMentionService
) : AbstractChainedStatusesProcessor(nextProcessor) {

    override fun process(mentions: List<Mention>?) {
        storeMostRecentMentionService.store(mentions)
    }
}

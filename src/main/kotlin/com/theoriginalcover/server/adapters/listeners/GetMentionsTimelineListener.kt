package com.theoriginalcover.server.adapters.listeners

import com.theoriginalcover.server.application.features.MostRecentMentionService
import com.theoriginalcover.server.application.twitter.mentions.GetMentionsTimelineService
import com.theoriginalcover.server.domain.IMentionsMapper
import io.micronaut.context.annotation.Context
import io.micronaut.scheduling.annotation.Scheduled
import org.slf4j.LoggerFactory
import twitter4j.*

@Context
class GetMentionsTimelineListener(
    private val mostRecentMentionService: MostRecentMentionService,
    private val getMentionsTimelineService: GetMentionsTimelineService,
    private val mentionsMapper: IMentionsMapper<ResponseList<Status>>
) : TwitterAdapter() {

    companion object {
        private val logger = LoggerFactory.getLogger(GetMentionsTimelineListener::class.java)
    }

    init {
        AsyncTwitterFactory
            .getSingleton()
            .addListener(this)
    }

    @Scheduled(fixedRate = "\${tocserver.adapters.listeners.get-mentions-timeline.fixed-rate:15s}")
    fun callGetMentions() {
        val mention: Long? = mostRecentMentionService.get()
        val factory = AsyncTwitterFactory.getSingleton()
        if (mention == null) factory.getMentions() else factory.getMentions(Paging(mention))
    }

    override fun gotMentions(statuses: ResponseList<Status>?) {
        logger.info("returned status: ${statuses?.size}")
        getMentionsTimelineService.process(
            mentionsMapper.map(statuses))
    }

    override fun onException(te: TwitterException?, method: TwitterMethod?) {
        logger.error("fail calling method ${method?.name}", te)
    }
}

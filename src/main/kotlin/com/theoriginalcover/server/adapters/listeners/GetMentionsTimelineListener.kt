package com.theoriginalcover.server.adapters.listeners

import com.theoriginalcover.server.application.twitter.mentions.GetMentionsTimelineService
import com.theoriginalcover.server.domain.IMentionsMapper
import io.micronaut.context.annotation.Context
import io.micronaut.scheduling.annotation.Scheduled
import org.slf4j.LoggerFactory
import twitter4j.*

@Context
class GetMentionsTimelineListener constructor(
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

    @Scheduled(fixedRate = "5s")
    fun callGetMentions() {
        AsyncTwitterFactory
            .getSingleton()
            .getMentions()
    }

    override fun gotMentions(statuses: ResponseList<Status>?) {
        logger.info("returned ${statuses?.size} status")
        getMentionsTimelineService.process(
            mentionsMapper.map(statuses))
    }

    override fun onException(te: TwitterException?, method: TwitterMethod?) {
        logger.error("fail calling method ${method?.name}", te)
    }
}

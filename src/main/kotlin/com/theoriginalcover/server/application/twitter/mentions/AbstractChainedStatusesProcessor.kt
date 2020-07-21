package com.theoriginalcover.server.application.twitter.mentions

import com.theoriginalcover.server.domain.Mention

abstract class AbstractChainedStatusesProcessor(private val nextProcessor: AbstractChainedStatusesProcessor?) {

    protected abstract fun process(mentions: List<Mention>?)

    fun execute(mentions: List<Mention>?) {
        process(mentions)
        nextProcessor?.execute(mentions)
    }
}

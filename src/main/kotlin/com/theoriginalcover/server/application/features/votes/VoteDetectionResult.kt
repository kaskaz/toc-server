package com.theoriginalcover.server.application.features.votes

import com.theoriginalcover.server.domain.VoteType

enum class VoteDetectionResult {
    ORIGINAL, COVER, NOT_VOTE;

    fun type(): VoteType? = when (this) {
        ORIGINAL -> VoteType.ORIGINAL
        COVER -> VoteType.COVER
        NOT_VOTE -> null
    }
}

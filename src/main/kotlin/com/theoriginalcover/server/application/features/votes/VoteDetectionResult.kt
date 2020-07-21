package com.theoriginalcover.server.application.features.votes

enum class VoteDetectionResult {
    ORIGINAL, COVER, NOT_VOTE;

    fun isOriginal(): Boolean = when (this) {
        ORIGINAL -> true
        COVER, NOT_VOTE -> false
    }
}

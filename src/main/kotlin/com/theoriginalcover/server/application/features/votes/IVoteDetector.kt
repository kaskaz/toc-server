package com.theoriginalcover.server.application.features.votes

interface IVoteDetector {
    fun detect(content: VoteDetectableContent): VoteDetectionResult
}

package com.theoriginalcover.server.application.features.votes

import javax.inject.Singleton

@Singleton
class VoteDetectorBasicImpl : IVoteDetector {

    companion object {
        private const val VOTE_PATTERN_ORIGINAL = "i vote original!"
        private const val VOTE_PATTERN_COVER = "i vote cover!"
    }

    override fun detect(content: VoteDetectableContent): VoteDetectionResult {
        val lcText = content.text.toLowerCase()

        return if (lcText.contains(VOTE_PATTERN_ORIGINAL))
            VoteDetectionResult.ORIGINAL
        else if (lcText.contains(VOTE_PATTERN_COVER))
            VoteDetectionResult.COVER
        else
            VoteDetectionResult.NOT_VOTE
    }
}

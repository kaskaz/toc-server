package com.theoriginalcover.server.aplication.features.votes

import com.theoriginalcover.server.application.features.votes.VoteDetectableContent
import com.theoriginalcover.server.application.features.votes.VoteDetectionResult
import com.theoriginalcover.server.application.features.votes.VoteDetectorBasicImpl
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class VoteDetectorBasicImplTest {

    private val voteDetectorBasicImpl = VoteDetectorBasicImpl()

    companion object {
        private const val TEXT_INVALID = "ABC1233453ZZCZ"
        private const val TEXT_DEFAULT_ORIGINAL = "I vote original!"
        private const val TEXT_ORIGINAL = "Joe cockerI vote original!"
        private const val TEXT_DEFAULT_COVER = "I vote cover!"
        private const val TEXT_COVER = "Joe cockerI vote cover!"
    }

    private fun assert(text: String, result: VoteDetectionResult) {
        val content = VoteDetectableContent(text = text)
        assert(voteDetectorBasicImpl.detect(content) == result)
    }

    @Test
    @DisplayName("should not detect vote for invalid text")
    fun shouldNotDetectVoteForInvalidText() {
        assert(TEXT_INVALID, VoteDetectionResult.NOT_VOTE)
    }

    @Test
    @DisplayName("should detect vote for default original text")
    fun shouldDetectVoteForDefaultOriginalText() {
        assert(TEXT_DEFAULT_ORIGINAL, VoteDetectionResult.ORIGINAL)
    }

    @Test
    @DisplayName("should detect vote for original text")
    fun shouldDetectVoteForOriginalText() {
        assert(TEXT_ORIGINAL, VoteDetectionResult.ORIGINAL)
    }

    @Test
    @DisplayName("should detect vote for default cover text")
    fun shouldDetectVoteForDefaultCoverText() {
        assert(TEXT_DEFAULT_COVER, VoteDetectionResult.COVER)
    }

    @Test
    @DisplayName("should detect vote for cover text")
    fun shouldDetectVoteForCoverText() {
        assert(TEXT_COVER, VoteDetectionResult.COVER)
    }
}

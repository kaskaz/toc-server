package com.theoriginalcover.server.aplication.features

import com.theoriginalcover.server.adapters.repositories.IVoteRepository
import com.theoriginalcover.server.application.features.votes.*
import com.theoriginalcover.server.domain.Mention
import com.theoriginalcover.server.domain.User
import com.theoriginalcover.server.domain.Vote
import io.mockk.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.util.*
import kotlin.random.Random

class VoteCounterServiceTest {

    @Test
    @DisplayName("should not count vote when status already exists")
    fun shouldNotCountVoteWhenStatusAlreadyExists() {
        val votesRepository = mockk<IVoteRepository>()
        val voteDetector = mockk<IVoteDetector>()
        val voteConfirmer = mockk<IVoteConfirmer>()

        val utVoteCounterService = VoteCounterService(votesRepository, voteDetector, voteConfirmer)

        val existingPoll = Random.nextLong(10000, 100000000)
        val existingPollStr = existingPoll.toString()
        val existingStatus = Random.nextLong(10000, 100000000)
        val existingStatusStr = existingStatus.toString()

        every { votesRepository.getPollIds() } returns setOf(existingPollStr)
        every { votesRepository.getAllVotesByPoll(existingPollStr) } returns arrayListOf(
            Vote(
                statusId = existingStatusStr,
                pollId = existingPollStr,
                userId = UUID.randomUUID().toString(),
                text = UUID.randomUUID().toString(),
                isOriginalNotCover = false))

        utVoteCounterService.count(
            listOf(Mention(
                id = existingStatus, createdAt = Date(), text = UUID.randomUUID().toString(),
                inReplyToStatusId = existingPoll, inReplyToUserId = 0, place = null, user = null
            ))
        )

        verifyOrder {
            votesRepository.getPollIds()
            votesRepository.getAllVotesByPoll(existingPoll.toString())
        }

        confirmVerified(votesRepository, voteDetector, voteConfirmer)
    }

    @Test
    @DisplayName("should not count vote when user already exists")
    fun shouldNotCountVoteWhenUserAlreadyExists() {
        val votesRepository = mockk<IVoteRepository>()
        val voteDetector = mockk<IVoteDetector>()
        val voteConfirmer = mockk<IVoteConfirmer>()

        val utVoteCounterService = VoteCounterService(votesRepository, voteDetector, voteConfirmer)

        val existingPoll = Random.nextLong(10000, 100000000)
        val existingPollStr = existingPoll.toString()
        val existingUser = Random.nextLong(10000, 100000000)
        val existingUserStr = existingUser.toString()
        val mentionId = Random.nextLong(10000, 100000000)

        every { votesRepository.getPollIds() } returns setOf(existingPollStr)
        every { votesRepository.getAllVotesByPoll(existingPollStr) } returns arrayListOf(
            Vote(
                statusId = (mentionId + 1).toString(),
                pollId = existingPollStr,
                userId = existingUserStr,
                text = UUID.randomUUID().toString(),
                isOriginalNotCover = false))

        utVoteCounterService.count(
            listOf(Mention(
                id = mentionId,
                createdAt = Date(),
                text = UUID.randomUUID().toString(),
                inReplyToStatusId = existingPoll,
                inReplyToUserId = 0,
                place = null,
                user = User(
                    id = existingUser,
                    name = UUID.randomUUID().toString(),
                    location = UUID.randomUUID().toString(),
                    isFollowRequestSent = false)
            ))
        )

        verifyOrder {
            votesRepository.getPollIds()
            votesRepository.getAllVotesByPoll(existingPoll.toString())
        }

        confirmVerified(votesRepository, voteDetector, voteConfirmer)
    }

    @Test
    @DisplayName("should not count vote if text is invalid for vote")
    fun shouldNotCountVoteIfTextIsInvalid() {
        val votesRepository = mockk<IVoteRepository>()
        val voteDetector = mockk<IVoteDetector>()
        val voteConfirmer = mockk<IVoteConfirmer>()

        val utVoteCounterService = VoteCounterService(votesRepository, voteDetector, voteConfirmer)

        val existingPoll = Random.nextLong(10000, 100000000)
        val existingPollStr = existingPoll.toString()
        val existingUser = Random.nextLong(10000, 100000000)
        val mentionId = Random.nextLong(10000, 100000000)
        val voteText = UUID.randomUUID().toString()

        val vote = Vote(
            statusId = (mentionId + 1).toString(),
            pollId = existingPollStr,
            userId = (existingUser + 1).toString(),
            text = voteText,
            isOriginalNotCover = false)

        val mention = Mention(
            id = mentionId,
            createdAt = Date(),
            text = voteText,
            inReplyToStatusId = existingPoll,
            inReplyToUserId = 0,
            place = null,
            user = User(
                id = existingUser,
                name = UUID.randomUUID().toString(),
                location = UUID.randomUUID().toString(),
                isFollowRequestSent = false)
        )

        val content = VoteDetectableContent(text = voteText)

        every { votesRepository.getPollIds() } returns setOf(existingPollStr)
        every { votesRepository.getAllVotesByPoll(existingPollStr) } returns arrayListOf(vote)
        every { voteDetector.detect(content) } returns VoteDetectionResult.NOT_VOTE

        utVoteCounterService.count(listOf(mention))

        verifyOrder {
            votesRepository.getPollIds()
            votesRepository.getAllVotesByPoll(existingPollStr)
            voteDetector.detect(content)
        }

        confirmVerified(votesRepository, voteDetector, voteConfirmer)
    }

    @Test
    @DisplayName("should count vote otherwise")
    fun shouldCountVoteOtherwise() {
        val votesRepository = mockk<IVoteRepository>()
        val voteDetector = mockk<IVoteDetector>()
        val voteConfirmer = mockk<IVoteConfirmer>()

        val utVoteCounterService = VoteCounterService(votesRepository, voteDetector, voteConfirmer)

        val existingPoll = Random.nextLong(10000, 100000000)
        val existingPollStr = existingPoll.toString()
        val newUser = Random.nextLong(10000, 100000000)
        val mentionId = Random.nextLong(10000, 100000000)
        val voteText = UUID.randomUUID().toString()

        val vote = Vote(
            statusId = mentionId.toString(),
            pollId = existingPollStr,
            userId = newUser.toString(),
            text = voteText,
            isOriginalNotCover = true)

        val mention = Mention(
            id = mentionId,
            createdAt = Date(),
            text = voteText,
            inReplyToStatusId = existingPoll,
            inReplyToUserId = 0,
            place = null,
            user = User(
                id = newUser,
                name = UUID.randomUUID().toString(),
                location = UUID.randomUUID().toString(),
                isFollowRequestSent = false)
        )

        val content = VoteDetectableContent(text = voteText)

        every { votesRepository.getPollIds() } returns setOf(existingPollStr)
        every { votesRepository.getAllVotesByPoll(existingPollStr) } returns emptyList()
        every { voteDetector.detect(content) } returns VoteDetectionResult.ORIGINAL
        every { votesRepository.save(vote) } just runs
        justRun { voteConfirmer.confirm(vote, mention) }

        utVoteCounterService.count(listOf(mention))

        verifyOrder {
            votesRepository.getPollIds()
            votesRepository.getAllVotesByPoll(existingPollStr)
            voteDetector.detect(content)
            votesRepository.save(vote)
            voteConfirmer.confirm(vote, mention)
        }

        confirmVerified(votesRepository, voteDetector, voteConfirmer)
    }
}

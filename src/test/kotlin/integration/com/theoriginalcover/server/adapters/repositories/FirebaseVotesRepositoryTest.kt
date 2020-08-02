package integration.com.theoriginalcover.server.adapters.repositories

import com.theoriginalcover.server.adapters.repositories.firebase.FirebaseVotesRepository
import com.theoriginalcover.server.domain.Vote
import com.theoriginalcover.server.domain.VoteType
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.util.*

class FirebaseVotesRepositoryTest {

    companion object {
        val POLL_ID = "integration-test-poll"
        val POLL_IDS = setOf<String>(POLL_ID, POLL_ID.plus("-additional"))
        val VOTES = listOf<Vote>(
            Vote("original-1", POLL_ID, "user-1", "text-1", VoteType.ORIGINAL),
            Vote("original-2", POLL_ID, "user-2", "text-2", VoteType.ORIGINAL),
            Vote("original-3", POLL_ID, "user-3", "text-3", VoteType.ORIGINAL),
            Vote("cover-1", POLL_ID, "user-4", "text-4", VoteType.COVER),
            Vote("cover-2", POLL_ID, "user-5", "text-5", VoteType.COVER))
    }

    @Test
    @DisplayName("should retrieve poll ids from database")
    fun shouldRetrievePollIdsFromDatabase() {
        val ids = FirebaseVotesRepository().getPollIds()
        assertTrue(ids.containsAll(POLL_IDS))
    }

    @Test
    @DisplayName("should retrieve votes by poll id from database")
    fun shouldRetrieveVotesByPollIdFromDatabase() {
        val votes = FirebaseVotesRepository().getAllVotesByPoll(POLL_ID)
        assertTrue(votes.containsAll(VOTES))
    }

    @Test
    @DisplayName("should save votes to database")
    fun shouldSaveVotesToDatabase() {

        val votesRepositoryImpl = FirebaseVotesRepository()

        val firstVote = Vote(
            statusId = UUID.randomUUID().toString(),
            pollId = "it-".plus(UUID.randomUUID().toString()),
            userId = UUID.randomUUID().toString(),
            text = UUID.randomUUID().toString(),
            type = VoteType.ORIGINAL
        )

        votesRepositoryImpl.save(firstVote)
        var votes = votesRepositoryImpl.getAllVotesByPoll(firstVote.pollId)
        assertTrue(votes.contains(firstVote))

        val secondVote = Vote(
            statusId = UUID.randomUUID().toString(),
            pollId = firstVote.pollId,
            userId = UUID.randomUUID().toString(),
            text = UUID.randomUUID().toString(),
            type = VoteType.ORIGINAL
        )

        votesRepositoryImpl.save(secondVote)
        votes = votesRepositoryImpl.getAllVotesByPoll(secondVote.pollId)
        assertTrue(votes.containsAll(listOf(firstVote, secondVote)))

        val thirdVote = Vote(
            statusId = UUID.randomUUID().toString(),
            pollId = firstVote.pollId,
            userId = UUID.randomUUID().toString(),
            text = UUID.randomUUID().toString(),
            type = VoteType.COVER
        )

        votesRepositoryImpl.save(thirdVote)
        votes = votesRepositoryImpl.getAllVotesByPoll(thirdVote.pollId)
        assertTrue(votes.containsAll(listOf(firstVote, secondVote, thirdVote)))
    }
}

package com.theoriginalcover.server.adapters.repositories

import com.theoriginalcover.server.domain.Vote
import javax.inject.Singleton

@Singleton
class VotesRepositoryImpl : IVoteRepository {

    override fun getPollIds(): Set<String> {
        return emptySet()
    }

    override fun getAllVotesByPoll(pollId: String): List<Vote> {
        return emptyList()
    }

    override fun save(vote: Vote) {
    }
}

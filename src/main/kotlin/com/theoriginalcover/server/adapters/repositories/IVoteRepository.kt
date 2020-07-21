package com.theoriginalcover.server.adapters.repositories

import com.theoriginalcover.server.domain.Vote

interface IVoteRepository {
    /**
     * Returns all votes aggregated from a poll (original and cover)
     */
    fun getAllVotesByPoll(pollId: String): List<Vote>

    fun getPollIds(): Set<String>
    fun save(vote: Vote)
}

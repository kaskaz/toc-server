package com.theoriginalcover.server.adapters.repositories.firebase

import com.google.cloud.firestore.Precondition
import com.theoriginalcover.server.adapters.repositories.IVoteRepository
import com.theoriginalcover.server.domain.Vote
import com.theoriginalcover.server.domain.VoteType
import javax.inject.Singleton

@Singleton
class FirebaseVotesRepository : FirebaseRepository(), IVoteRepository {

    companion object {
        private const val COLLECTION = "votes"
        private const val FIELD_STATUS_ID = "statusId"
        private const val FIELD_USER_ID = "userId"
        private const val FIELD_TEXT = "text"
    }

    override fun getPollIds(): Set<String> {
        return getDatabase()
            .collection(COLLECTION)
            .listDocuments()
            .map { it.id }
            .toHashSet()
    }

    override fun getAllVotesByPoll(pollId: String): List<Vote> {
        val poll = getDatabase()
            .collection(COLLECTION)
            .document(pollId).get().get()

        val list = ArrayList<Vote>()

        val original = poll.get("original")
        if (original != null)
            mapVoteToList((original as ArrayList<HashMap<String, String>>), pollId, VoteType.ORIGINAL, list)

        val cover = poll.get("cover")
        if (cover != null)
            mapVoteToList((cover as ArrayList<HashMap<String, String>>), pollId, VoteType.COVER, list)

        return list
    }

    override fun save(vote: Vote) {
        val poll = getDatabase()
            .collection(COLLECTION)
            .document(vote.pollId)

        var data = poll.get().get().get(vote.type.label())
        val map = mapVoteToMap(vote)

        if (data == null) data = arrayListOf(map) else (data as ArrayList<Map<String, String>>).add(map)

        poll.update(mapOf(Pair(vote.type.label(), data)), Precondition.NONE)
            .get()
    }

    private fun mapVoteToList(votes: ArrayList<HashMap<String, String>>, pollId: String, type: VoteType, list: ArrayList<Vote>) {
        votes.forEach {
            list.add(Vote(it[FIELD_STATUS_ID]!!, pollId, it[FIELD_USER_ID]!!, it[FIELD_TEXT]!!, type))
        }
    }

    private fun mapVoteToMap(vote: Vote): Map<String, String> {
        return mapOf(
            Pair(FIELD_STATUS_ID, vote.statusId),
            Pair(FIELD_USER_ID, vote.userId),
            Pair(FIELD_TEXT, vote.text)
        )
    }
}

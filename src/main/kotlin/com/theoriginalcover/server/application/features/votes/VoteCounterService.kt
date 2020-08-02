package com.theoriginalcover.server.application.features.votes

import com.theoriginalcover.server.adapters.repositories.IVoteRepository
import com.theoriginalcover.server.domain.Mention
import com.theoriginalcover.server.domain.Vote

class VoteCounterService(
    private val voteRepository: IVoteRepository,
    private val voteDetector: IVoteDetector,
    private val voteConfirmer: IVoteConfirmer
) {

    fun count(mentions: List<Mention>?) {
        if (mentions == null)
            return

        val groupedMentions = groupMentionsbyPollId(mentions)

        groupedMentions.forEach { (pollId, mentionsList) ->
            val votes = voteRepository.getAllVotesByPoll(pollId)

            mentionsList.forEach {
                if (isNewVote(votes, it)) {
                    saveAndConfirmVote(it)
                }
            }
        }
    }

    private fun groupMentionsbyPollId(mentions: List<Mention>): HashMap<String, ArrayList<Mention>> {
        val pollIds = voteRepository.getPollIds()
        val groupedMentions = HashMap<String, ArrayList<Mention>>()

        mentions.forEach {
            val inReplyToStatusId = it.inReplyToStatusId.toString()

            if (pollIds.contains(inReplyToStatusId)) {

                if (groupedMentions.containsKey(inReplyToStatusId))
                    groupedMentions[inReplyToStatusId]?.add(it)
                else
                    groupedMentions[inReplyToStatusId] = arrayListOf(it)
            }
        }

        return groupedMentions
    }

    private fun isNewVote(existingVotes: List<Vote>, prospectVote: Mention): Boolean {
        return existingVotes.none { vote ->
            vote.statusId == prospectVote.id.toString() || vote.userId == prospectVote.user?.id.toString() }
    }

    private fun saveAndConfirmVote(mention: Mention) {
        if (mention.text == null)
            return

        val content = VoteDetectableContent(text = mention.text!!)
        val result = voteDetector.detect(content)

        if (result == VoteDetectionResult.NOT_VOTE)
            return

        val vote = Vote(
            statusId = mention.id.toString(),
            pollId = mention.inReplyToStatusId.toString(),
            userId = mention.user?.id.toString(),
            text = mention.text!!,
            type = result.type()!!)

        voteRepository.save(vote)
        voteConfirmer.confirm(vote, mention)
    }
}

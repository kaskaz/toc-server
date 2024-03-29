package com.theoriginalcover.server.application.features.votes

import com.theoriginalcover.server.adapters.listeners.GetMentionsTimelineListener
import com.theoriginalcover.server.adapters.repositories.IVoteRepository
import com.theoriginalcover.server.domain.Mention
import com.theoriginalcover.server.domain.Vote
import org.slf4j.LoggerFactory
import javax.inject.Singleton

@Singleton
class VoteCounterService(
    private val voteRepository: IVoteRepository,
    private val voteDetector: IVoteDetector,
    private val voteConfirmer: IVoteConfirmer
) {

    companion object {
        private val logger = LoggerFactory.getLogger(VoteCounterService::class.java)
    }

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
        logger.info("vote saved: $vote")

        voteConfirmer.confirm(vote, mention)
    }
}

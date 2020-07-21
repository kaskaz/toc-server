package com.theoriginalcover.server.application.features.votes

import com.theoriginalcover.server.domain.Mention
import com.theoriginalcover.server.domain.Vote

interface IVoteConfirmer {
    fun confirm(vote: Vote, mention: Mention)
}

package com.theoriginalcover.server.domain

import java.util.*

data class Mention(
    var id: Long?,
    var createdAt: Date?,
    var text: String?,
    var inReplyToStatusId: Long?,
    var inReplyToUserId: Long?,
    var place: Place?,
    var user: User?
) {
    constructor() : this(null, null, null, null, null, null, null)
}

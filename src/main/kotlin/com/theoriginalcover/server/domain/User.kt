package com.theoriginalcover.server.domain

data class User(
    var id: Long?,
    var name: String?,
    var location: String?,
    var isFollowRequestSent: Boolean?
) {
    constructor() : this(null, null, null, null)
}

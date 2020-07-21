package com.theoriginalcover.server.domain

data class User(
    val id: Long,
    val name: String,
    val location: String,
    val isFollowRequestSent: Boolean
)

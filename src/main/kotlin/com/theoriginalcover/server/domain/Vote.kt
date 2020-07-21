package com.theoriginalcover.server.domain

data class Vote(
    val statusId: String,
    val pollId: String,
    val userId: String,
    val text: String,
    var isOriginalNotCover: Boolean
)

package com.theoriginalcover.server.domain

enum class VoteType {
    ORIGINAL, COVER;

    fun label(): String {
        return when (this) {
            ORIGINAL -> "original"
            COVER -> "cover"
        }
    }
}

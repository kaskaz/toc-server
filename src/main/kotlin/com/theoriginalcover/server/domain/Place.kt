package com.theoriginalcover.server.domain

data class Place(
    val id: Long,
    val fullName: String,
    val country: String,
    val countryCode: String
)

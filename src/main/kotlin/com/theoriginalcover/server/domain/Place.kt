package com.theoriginalcover.server.domain

data class Place(
    var id: Long?,
    var fullName: String?,
    var country: String?,
    var countryCode: String?
) {
    constructor() : this(null, null, null, null)
}

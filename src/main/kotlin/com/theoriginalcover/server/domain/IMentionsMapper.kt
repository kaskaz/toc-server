package com.theoriginalcover.server.domain

interface IMentionsMapper<T> {
    fun map(list: T?): List<Mention>
}

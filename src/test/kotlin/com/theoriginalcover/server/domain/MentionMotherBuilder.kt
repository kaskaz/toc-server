package com.theoriginalcover.server.domain

import java.util.*
import kotlin.random.Random

class MentionMotherBuilder {

    private var id: Long? = null
    private var createdAt: Date? = null
    private var text: String? = null
    private var inReplyToStatusId: Long? = null
    private var inReplyToUserId: Long? = null
    private var place: Place? = null
    private var user: User? = null

    fun build(): Mention {
        return Mention(
            id = if (id != null) id else Random.nextLong(),
            createdAt = if (createdAt != null) createdAt else Date(),
            text = if (text != null) text else UUID.randomUUID().toString(),
            inReplyToStatusId = Random.nextLong(),
            inReplyToUserId = Random.nextLong(),
            place = place,
            user = user
        )
    }

    fun withId(id: Long): MentionMotherBuilder { this.id = id; return this }
    fun withCreatedAt(createdAt: Date): MentionMotherBuilder { this.createdAt = createdAt; return this }
    fun withText(text: String): MentionMotherBuilder { this.text = text; return this }
    fun withInReplyToStatusId(inReplyToStatusId: Long): MentionMotherBuilder {
        this.inReplyToStatusId = inReplyToStatusId; return this }
    fun withInReplyToUserId(inReplyToUserId: Long): MentionMotherBuilder {
        this.inReplyToUserId = inReplyToUserId; return this }
    fun withPlace(place: Place): MentionMotherBuilder { this.place = place; return this }
    fun withUser(user: User): MentionMotherBuilder { this.user = user; return this }
}

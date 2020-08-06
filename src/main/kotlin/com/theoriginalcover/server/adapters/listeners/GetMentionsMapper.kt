package com.theoriginalcover.server.adapters.listeners

import com.github.dozermapper.core.DozerBeanMapperBuilder
import com.github.dozermapper.core.Mapper
import com.theoriginalcover.server.domain.IMentionsMapper
import com.theoriginalcover.server.domain.Mention
import twitter4j.ResponseList
import twitter4j.Status
import javax.inject.Singleton

@Singleton
class GetMentionsMapper : IMentionsMapper<ResponseList<Status>> {

    private var mapper: Mapper = DozerBeanMapperBuilder.buildDefault()
    private var clazz: Class<Mention> = Mention::class.java

    override fun map(list: ResponseList<Status>?): List<Mention> {
        val output = ArrayList<Mention>()
        list?.forEach { status -> output.add(mapper.map(status, clazz)) }
        return output
    }
}

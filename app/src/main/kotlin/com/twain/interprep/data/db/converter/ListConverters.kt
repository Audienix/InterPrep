package com.twain.interprep.data.db.converter

import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.twain.interprep.data.model.Question
import com.twain.interprep.data.model.ResourceLink

class ListConverters {

    private val moshi: Moshi = Moshi.Builder().build()
    @TypeConverter
    fun fromJsonToQuestionList(json: String?): List<Question>? {
        if (json == null) return null
        val type = Types.newParameterizedType(List::class.java, Question::class.java)
        val adapter = moshi.adapter<List<Question>>(type)
        return adapter.fromJson(json)
    }

    @TypeConverter
    fun fromQuestionListToJson(questions: List<Question>?): String? {
        if (questions == null) return null
        val type = Types.newParameterizedType(List::class.java, Question::class.java)
        val adapter = moshi.adapter<List<Question>>(type)
        return adapter.toJson(questions)
    }

    @TypeConverter
    fun fromJsonToResourceLinkList(json: String?): List<ResourceLink>? {
        if (json == null) return null
        val type = Types.newParameterizedType(List::class.java, ResourceLink::class.java)
        val adapter = moshi.adapter<List<ResourceLink>>(type)
        return adapter.fromJson(json)
    }

    @TypeConverter
    fun fromResourceLinkListToJson(links: List<ResourceLink>?): String? {
        if (links == null) return null
        val type = Types.newParameterizedType(List::class.java, ResourceLink::class.java)
        val adapter = moshi.adapter<List<ResourceLink>>(type)
        return adapter.toJson(links)
    }
}
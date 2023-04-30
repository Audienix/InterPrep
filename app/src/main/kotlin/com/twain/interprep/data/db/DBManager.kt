package com.twain.interprep.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.twain.interprep.data.dao.InterviewDAO
import com.twain.interprep.data.dao.NoteDAO
import com.twain.interprep.data.dao.QuoteDAO
import com.twain.interprep.data.dao.ResourceDAO
import com.twain.interprep.data.db.converter.ListConverter
import com.twain.interprep.data.db.converter.DateConverter
import com.twain.interprep.data.db.converter.EnumConverter
import com.twain.interprep.data.model.Interview
import com.twain.interprep.data.model.Note
import com.twain.interprep.data.model.Question
import com.twain.interprep.data.model.Quote
import com.twain.interprep.data.model.Resource
import com.twain.interprep.data.model.ResourceLink
import com.twain.interprep.data.model.Subtopic
import com.twain.interprep.data.model.Topic

@Database(
    entities = [Interview::class, Note::class, Question::class, Quote::class, Resource::class,
        ResourceLink::class, Subtopic::class, Topic::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(ListConverter::class, DateConverter::class, EnumConverter::class)
abstract class DBManager : RoomDatabase() {
    abstract fun interviewDao(): InterviewDAO
    abstract fun noteDao(): NoteDAO
    abstract fun resourceDao(): ResourceDAO
    abstract fun quoteDao(): QuoteDAO
}
package com.twain.interprep.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.twain.interprep.data.dao.InterviewDAO
import com.twain.interprep.data.model.Interview

@Database(entities = [Interview::class], version = 1, exportSchema = false)
abstract class DBManager : RoomDatabase() {
    abstract fun interviewDao(): InterviewDAO
}
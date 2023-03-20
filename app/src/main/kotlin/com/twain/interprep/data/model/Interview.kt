package com.twain.interprep.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Calendar

@Entity
data class Interview (
    @PrimaryKey(autoGenerate = true)
    var interviewId: Int = 0,
    var interviewDateTime: Long = Calendar.getInstance().timeInMillis,
)
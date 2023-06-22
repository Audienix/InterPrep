package com.twain.interprep.domain.repository

import com.twain.interprep.data.model.DropDownOption
import com.twain.interprep.data.model.DropDownOptionType
import kotlinx.coroutines.flow.Flow

interface DropDownRepository {
    suspend fun insertDropDownOption(dropDownOption: DropDownOption)

    fun getAllDropDownOptions(type: DropDownOptionType): Flow<List<String>>
}

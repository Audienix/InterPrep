package com.twain.interprep.data.repository

import androidx.annotation.WorkerThread
import com.twain.interprep.data.dao.DropDownDao
import com.twain.interprep.data.model.DropDownOption
import com.twain.interprep.data.model.DropDownOptionType
import com.twain.interprep.domain.repository.DropDownRepository
import kotlinx.coroutines.flow.Flow

class DropDownOptionRepositoryImpl(private val dropDownDao: DropDownDao) : DropDownRepository {
    @WorkerThread
    override suspend fun insertDropDownOption(dropDownOption: DropDownOption) {
        dropDownDao.insertDropDownOption(dropDownOption)
    }

    @WorkerThread
    override fun getAllDropDownOptions(type: DropDownOptionType): Flow<List<String>> =
        dropDownDao.getAllDropDownOptions(type)
}

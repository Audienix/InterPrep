package com.twain.interprep.domain.usecase.dropDownOption

import com.twain.interprep.data.model.DropDownOption
import com.twain.interprep.domain.repository.DropDownRepository

class InsertDropDownOptionsUseCase(private val dropDownRepository: DropDownRepository) {

    suspend operator fun invoke(dropDownOption: DropDownOption) {
        dropDownRepository.insertDropDownOption(dropDownOption)
    }
}

package com.twain.interprep.domain.usecase.dropDownOption

import com.twain.interprep.data.model.DropDownOptionType
import com.twain.interprep.domain.repository.DropDownRepository

class GetDropDownOptionsUseCase(private val dropDownRepository: DropDownRepository) {

    operator fun invoke(type: DropDownOptionType) = dropDownRepository.getAllDropDownOptions(type)
}

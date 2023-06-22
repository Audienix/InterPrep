package com.twain.interprep.domain.usecase.dropDownOption

data class DropDownOptionUseCase(
    val getDropDownOptions: GetDropDownOptionsUseCase,
    val insertDropDownOption: InsertDropDownOptionsUseCase,
)

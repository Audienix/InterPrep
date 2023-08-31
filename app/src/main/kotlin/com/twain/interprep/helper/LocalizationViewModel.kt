package com.twain.interprep.helper

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.twain.interprep.R
import com.twain.interprep.datastore.usecase.DataStoreUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class LocalizationViewModel @Inject constructor(
    private val dataStoreUseCase: DataStoreUseCase
) : ViewModel() {

    private var selectedLanguageCode: String = "en"
    private var languageMap = mapOf<String, String>()

    fun initialize(context: Context) {
        setLanguageCodeMapper(context = context)
        viewModelScope.launch {
            setAppLanguage(context = context)
        }
    }

    private fun setLanguageCodeMapper(context: Context) {
        val array = context.resources.getStringArray(R.array.language_option)
        languageMap = mapOf(
            array[0] to "en",
            array[1] to "es",
            array[2] to "zh"
        )
    }

    private suspend fun setAppLanguage(context: Context) {
        dataStoreUseCase.languageUseCase.getLanguage().collect {
            selectedLanguageCode = it
            setLocale(context, getLanguageCode(selectedLanguageCode), true)
        }
    }

    fun setLocale(context: Context, languageCode: String, isInitial: Boolean) {
        val resources = context.resources
        var locale = Locale(languageCode)
        if (languageCode == context.resources.getStringArray(R.array.language_code)[2])
            locale = Locale(languageCode, "CN")

        val config = Configuration(resources.configuration)
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
        if (!isInitial)
            context.startActivity(Intent(context, context::class.java))
    }

    fun getLanguageCode(language: String): String = languageMap[language].toString()

    @Composable
    fun getString(context: Context, stringResId: Int): String {
        val resources = context.resources
        val config = Configuration(resources.configuration)
        config.setLocale(Locale(selectedLanguageCode))
        val localizedContext = context.createConfigurationContext(config)
        return localizedContext.resources.getString(stringResId)
    }
}

package com.twain.interprep.datastore

import com.twain.interprep.presentation.ui.modules.profile.ProfileSettings
import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {

    fun getProfileSettings() : Flow<ProfileSettings>
}

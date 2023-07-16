package com.twain.interprep.presentation.ui.modules.resources

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.twain.interprep.data.model.Resource
import com.twain.interprep.data.model.ResourceLink
import com.twain.interprep.data.model.ViewResult
import com.twain.interprep.domain.usecase.resource.ResourceUseCase
import com.twain.interprep.helper.CoroutineContextDispatcher
import com.twain.interprep.presentation.ui.modules.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CoroutineExceptionHandler

@HiltViewModel
class ResourcesViewModel @Inject constructor(
    contextProvider: CoroutineContextDispatcher,
    private val resourceUseCase: ResourceUseCase
) : BaseViewModel(contextProvider) {

    override val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
//        val message = ExceptionHandler.parse(exception)
    }

    var resourceAndLinksPair: ViewResult<List<Pair<Resource, List<ResourceLink>>>> by mutableStateOf(
        ViewResult.UnInitialized
    )

    fun getResourceAndLinksPair() = launchCoroutineIO {
        resourceUseCase.getAllResourcesWithLinksUseCase().collect {
            resourceAndLinksPair = ViewResult.Loaded(it)
        }
    }
}

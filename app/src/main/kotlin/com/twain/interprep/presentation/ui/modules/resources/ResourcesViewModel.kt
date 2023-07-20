package com.twain.interprep.presentation.ui.modules.resources

import androidx.annotation.StringRes
import com.twain.interprep.domain.usecase.resourceLink.ResourceLinkUseCase
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.twain.interprep.R
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
    private val resourceUseCase: ResourceUseCase,
    private val resourceLinkUseCase: ResourceLinkUseCase
) : BaseViewModel(contextProvider) {

    override val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
//        val message = ExceptionHandler.parse(exception)
    }
    var resource: ViewResult<Resource> by mutableStateOf(ViewResult.UnInitialized)
    val links = mutableStateListOf<ResourceLink>()
    var resourceAndLinksPair: ViewResult<List<Pair<Resource, List<ResourceLink>>>> by mutableStateOf(
        ViewResult.UnInitialized
    )

    fun getResourceAndLinksPair() = launchCoroutineIO {
        resourceUseCase.getAllResourcesWithLinksUseCase().collect {
            resourceAndLinksPair = ViewResult.Loaded(it)
        }
    }

    fun getResourceAndLinksByResourceId(resourceId: Int) = launchCoroutineIO {
        if (resourceId == 0) {
            this@ResourcesViewModel.links.add(ResourceLink())
            this@ResourcesViewModel.resource = ViewResult.Loaded(Resource())
        } else {
            resourceUseCase.getResourceWithLinksByResourceId(resourceId)
                .collect { resourceWithLinks ->
                    this@ResourcesViewModel.resource = ViewResult.Loaded(resourceWithLinks.resource)
                    this@ResourcesViewModel.links.clear()
                    this@ResourcesViewModel.links.addAll(resourceWithLinks.links.sortedBy { it.linkId })
                }
        }
    }

    fun addLinkEnabled() = true

    fun addLink() = launchCoroutineIO {
        // insert the current resource
        val res = (resource as ViewResult.Loaded).data
        val resId = resourceUseCase.upsertResourceUseCase(res) ?: return@launchCoroutineIO

        // update the resource in the view model
        resource = ViewResult.Loaded(res.copy(resourceId = resId))

        // insert the last link in the links list to the database
        val index = links.size - 1
        val link = links[index]
        val linkId = resourceLinkUseCase.insertResourceLinkUseCase(link.copy(resourceId = resId))
            ?: return@launchCoroutineIO

        // update the last link instance in the links list in the view model
        links[index] = link.copy(linkId = linkId, resourceId = resId)

        // add one ResourceLink() in the view model
        this@ResourcesViewModel.links.add(ResourceLink())
    }



    fun addAllLinks() = launchCoroutineIO {
        val res = (resource as ViewResult.Loaded).data
        val resId = resourceUseCase.upsertResourceUseCase(res) ?: return@launchCoroutineIO

        resourceLinkUseCase.insertAllResourceLinkUseCase(links, resId)
    }

    fun deleteLink(link: ResourceLink) {
        launchCoroutineIO {
            resourceLinkUseCase.deleteResourceLinkUseCase(link)
        }
    }

    // delete link is cascaded
    fun deleteResource() {
        val res = (resource as ViewResult.Loaded<Resource>).data
        launchCoroutineIO {
            resourceUseCase.deleteResourceUseCase(res)
        }
    }

    fun getResourceField(@StringRes resId: Int): String {
        return if (resource is ViewResult.Loaded) {
            val data = (resource as ViewResult.Loaded<Resource>).data
            when (resId) {
                R.string.hint_label_topic -> data.topic
                R.string.hint_label_subtopic -> data.subtopic
                else -> ""
            }
        } else {
            ""
        }
    }

    fun getLinkField(@StringRes resId: Int, index: Int): String {
        val res = links[index].let {
            when (resId) {
                R.string.hint_label_link_description -> it.linkDescription
                R.string.hint_label_link -> it.link
                else -> ""
            }
        }
        return res
    }

    fun updateLinkField(@StringRes resId: Int, index: Int, value: String) {
        val link = links[index]
        when (resId) {
            R.string.hint_label_link_description -> {
                links[index] = link.copy(
                    linkDescription = value
                )
            }

            R.string.hint_label_link -> {
                links[index] = link.copy(
                    link = value
                )
            }
        }
    }

    fun updateResourceField(@StringRes resId: Int, value: String) {
        val data = (resource as ViewResult.Loaded<Resource>).data
        when (resId) {
            R.string.hint_label_topic -> {
                resource = ViewResult.Loaded(data.copy(topic = value))
            }

            R.string.hint_label_subtopic -> {
                resource = ViewResult.Loaded(data.copy(subtopic = value))
            }
        }
    }

    fun isResourceValid(): Boolean {
        val data = (resource as ViewResult.Loaded<Resource>).data
        return data.topic.isNotEmpty()
    }

    fun areAllLinksValid() = true
}

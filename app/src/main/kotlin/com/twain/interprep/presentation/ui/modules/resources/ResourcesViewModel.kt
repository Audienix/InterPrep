package com.twain.interprep.presentation.ui.modules.resources

import androidx.annotation.StringRes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.twain.interprep.R
import com.twain.interprep.data.model.Resource
import com.twain.interprep.data.model.ResourceLink
import com.twain.interprep.data.model.ViewResult
import com.twain.interprep.data.ui.ResourceFormData.linkForm
import com.twain.interprep.domain.usecase.resource.ResourceUseCase
import com.twain.interprep.domain.usecase.resourceLink.ResourceLinkUseCase
import com.twain.interprep.helper.CoroutineContextDispatcher
import com.twain.interprep.presentation.ui.modules.common.BaseViewModel
import com.twain.interprep.utils.isValidTextInput
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import javax.inject.Inject

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
    var resourceAndLinksPairs: ViewResult<List<Pair<Resource, List<ResourceLink>>>> by mutableStateOf(
        ViewResult.UnInitialized
    )
    var searchText by mutableStateOf("")

    /**
     * Fetch all information for the list of resource and links screen  [ResourcesScreen]
     *
     * @return pairs of Resource and its associated ResourceLinks
     */
    fun getResourceAndLinksPair() = launchCoroutineIO {
        resourceUseCase.getAllResourcesWithLinksUseCase().collect {
            resourceAndLinksPairs = ViewResult.Loaded(it)
        }
    }

    /**
     * Fetch all information for resource adding and editing screen [AddResourceScreen]
     *
     * @return the Resource based on the given resourceId and its associated ResourceLinks
     */
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

    /**
     * Add Resource to the database and then add the last ResourceLink stored in the view model
     * to the database with the corresponding resourceId.We can't insert Resource with replacing
     * policy, since ResourceLink is associated with Resource and is cascaded when deleting the
     * Resource.
     *
     * https://betterprogramming.pub/upserting-in-room-8207a100db53
     */
    fun addLink() = launchCoroutineIO {
        // upsert the current resource
        val res = (resource as ViewResult.Loaded).data
        val resId = resourceUseCase.upsertResourceUseCase(res) ?: return@launchCoroutineIO

        // update the resource in the view model
        resource = ViewResult.Loaded(res.copy(resourceId = resId))

        // insert the last link in the links list to the database
        val index = links.size - 1
        val link = links[index]
        val linkId = resourceLinkUseCase.insertResourceLinkUseCase(link.copy(resourceId = resId))
            ?: return@launchCoroutineIO

        // update the last link in the links list in the view model
        links[index] = link.copy(linkId = linkId, resourceId = resId)

        // add one ResourceLink() in the view model
        links.add(ResourceLink())
    }

    /**
     * Add Resource to the database and then add all ResourceLinks stored in the view model
     * to the database with the corresponding resourceId.We can't insert Resource with replacing
     * policy, since ResourceLink is associated with Resource and is cascaded when deleting the
     * Resource.
     *
     * https://betterprogramming.pub/upserting-in-room-8207a100db53
     */
    fun addAllLinks() = launchCoroutineIO {
        val res = (resource as ViewResult.Loaded).data
        val resId = resourceUseCase.upsertResourceUseCase(res) ?: return@launchCoroutineIO

        resourceLinkUseCase.insertAllResourceLinkUseCase(links, resId)
    }

    /**
     * Delete the given link instance in the database
     */
    fun deleteLink(link: ResourceLink) {
        launchCoroutineIO {
            resourceLinkUseCase.deleteResourceLinkUseCase(link)
            links.removeIf { it.linkId == link.linkId }
        }
    }

    /**
     * Delete the resource stored in the view model from the database and the associated
     * ResourceLinks will be deleted too
     */
    fun deleteResource() {
        val res = (resource as ViewResult.Loaded<Resource>).data
        launchCoroutineIO {
            resourceUseCase.deleteResourceUseCase(res)
        }
    }

    /**
     * Retrieve the value of instance property based on the given string resource
     */
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

    /**
     * Retrieve the value of instance property based on the given string resource
     */
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

    /**
     * Update the specified field of the ResourceLink instance in the view model.
     *
     * @param resId: the string resource to identify the field of the Resource Link to update
     * @param index: the index of the ResourceLink in the links stored in the view model
     * @param value: the value to update the field of ResourceLink to
     */
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

    /**
     * Update the specified field of the Resource instance in the view model.
     *
     * @param resId: the string resource to identify the field of the Resource to update
     * @param value: the value to update the field of Resource to
     */
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

    fun isResourceAndLinksValid(): Boolean {
        val resourceData = (resource as ViewResult.Loaded<Resource>).data
        val resourceValid = resourceData.topic.isNotEmpty()
        val linksValid =
            !links.any { link -> !isLinkValid(link) } // if any of link is not valid, links is not valid
        return resourceValid && linksValid
    }

    private fun isLinkValid(link: ResourceLink) = isValidTextInput(
        true, link.link, linkForm[1]
    )

    fun addLinkEnabled() = links.isEmpty() || isLinkValid(links.last())

    fun getSearchingResult(searchText: String = "") = launchCoroutineIO {
        resourceUseCase.getResourceWithLinksBySearchText(searchText).collect {
            val result = mutableListOf<Pair<Resource, List<ResourceLink>>>()
            it.forEach { resourceAndLinks ->
                result.add(resourceAndLinks.resource to resourceAndLinks.links)
            }
            resourceAndLinksPairs = ViewResult.Loaded(result)
        }
    }

    fun updateSearchText(text: String) {
        searchText = text
    }
}

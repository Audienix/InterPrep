package com.twain.interprep.domain.usecase.resourceLink

import android.util.Log
import com.twain.interprep.data.model.ResourceLink
import com.twain.interprep.domain.repository.ResourceLinkRepository

class InsertResourceLinkUseCase(private val resourceLinkRepository: ResourceLinkRepository) {
    suspend operator fun invoke(
        link: ResourceLink
    ): Int? {
        val id = if (isLinkValid(link)) {
            if (link.linkId == 0) {
                resourceLinkRepository.insertResourceLink(link)
            } else {
                link.linkId
            }
        } else {
            null
        }
        Log.i("add link", "linkId = ${id.toString()}")
        return id
    }


    private fun isLinkValid(link: ResourceLink) = true
}


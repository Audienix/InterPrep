package com.twain.interprep.data.ui

import com.twain.interprep.data.model.Resource
import com.twain.interprep.data.model.ResourceLink

val resourceLinksMockData = listOf(
    ResourceLink(
        linkId = 0,
        linkDescription = "UI State & Composition",
        link = "https://developer.android.com/jetpack/compose/state"
    ),
    ResourceLink(
        linkId = 1,
        linkDescription = "Compose layouts",
        link = "https://developer.android.com/jetpack/compose/layouts/basics"
    ),
    ResourceLink(
        linkId = 2,
        linkDescription = "Compose modifiers",
        link = "https://developer.android.com/jetpack/compose/modifiers"
    ),
    ResourceLink(
        linkId = 3,
        linkDescription = "STAR Method",
        link = "https://www.themuse.com/advice/star-interview-method"
    )
)

val resourcesMockData = listOf(
    Resource(
        resourceId = 0,
        topic = "Jetpack Compose",
        subtopic = "State Management",
        links = listOf(resourceLinksMockData[0])
    ),
    Resource(
        resourceId = 1,
        topic = "Jetpack Compose",
        subtopic = "",
        links = listOf(resourceLinksMockData[1], resourceLinksMockData[2])
    ),
    Resource(
        resourceId = 0,
        topic = "Behavioural Round",
        subtopic = "",
        links = listOf(resourceLinksMockData[3])
    )
)
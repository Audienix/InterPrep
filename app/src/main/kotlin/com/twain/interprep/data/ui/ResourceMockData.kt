package com.twain.interprep.data.ui

import com.twain.interprep.data.model.Resource
import com.twain.interprep.data.model.ResourceLink

val link1 = ResourceLink(
    linkId = 0,
    resourceId = 1,
    linkDescription = "UI State & Composition",
    link = "https://developer.android.com/jetpack/compose/state"
)
val link2 = ResourceLink(
    linkId = 1,
    resourceId = 1,
    linkDescription = "Compose layouts",
    link = "https://developer.android.com/jetpack/compose/layouts/basics"
)
val link3 = ResourceLink(
    linkId = 2,
    resourceId = 0,
    linkDescription = "Compose modifiers",
    link = "https://developer.android.com/jetpack/compose/modifiers"
)
val link4 = ResourceLink(
    linkId = 3,
    resourceId = 2,
    linkDescription = "STAR Method",
    link = "https://www.themuse.com/advice/star-interview-method"
)

val resource1 =
    Resource(
        resourceId = 0,
        topic = "Jetpack Compose",
        subtopic = "State Management"
    )
val resource2 =
    Resource(
        resourceId = 1,
        topic = "Jetpack Compose",
        subtopic = ""
    )
val resource3 =
    Resource(
        resourceId = 2,
        topic = "Behavioural Round",
        subtopic = ""
    )

val  resourceWithLinks: List<Pair<Resource, List<ResourceLink>>> = listOf(
    Pair(resource1, listOf(link3)),
    Pair(resource2, listOf(link1, link2)),
    Pair(resource3, listOf(link4))
)

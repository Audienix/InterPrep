package com.twain.interprep.presentation.ui.modules.dashboard

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue

// Define an enumeration representing scroll directions
enum class ScrollDirection { UP, DOWN }

// Class to encapsulate the logic for determining scroll direction based on LazyListState
class DirectionalLazyListState(lazyListState: LazyListState) {

    val scrollDirection by derivedStateOf {
        // Check if the first visible item is close to the top of the list
        if (lazyListState.firstVisibleItemIndex < 1 && lazyListState.firstVisibleItemScrollOffset < 1)
            ScrollDirection.UP
        else
            ScrollDirection.DOWN
    }
}

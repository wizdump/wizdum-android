package com.teamwizdum.wizdum.feature.onboarding.component

import androidx.compose.foundation.MutatePriority
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

// https://proandroiddev.com/infinite-auto-scrolling-lists-with-recyclerview-lazylists-in-compose-1c3b44448c8
@Composable
fun <T : Any> AutoScrollingLazyRow(
    list: List<T>,
    modifier: Modifier = Modifier,
    scrollDx: Float = SCROLL_DX,
    delayBetweenScrollMs: Long = DELAY_BETWEEN_SCROLL_MS,
    itemContent: @Composable (item: T) -> Unit,
) {
    var itemsListState by remember { mutableStateOf(list) }
    val lazyListState = rememberLazyListState()

    LazyRow(
        state = lazyListState,
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(34.dp)
    ) {
        items(itemsListState) {
            itemContent(item = it)

            if (it == itemsListState.last()) {

                LaunchedEffect(lazyListState.firstVisibleItemIndex) {
                    val currentList = itemsListState

                    val secondPart = currentList.subList(0, lazyListState.firstVisibleItemIndex)
                    val firstPart = currentList.subList(lazyListState.firstVisibleItemIndex, currentList.size)
                    lazyListState.scrollToItem(0, maxOf(0, lazyListState.firstVisibleItemScrollOffset - scrollDx.toInt()))
                    itemsListState = firstPart + secondPart
                }
            }
        }

    }
    LaunchedEffect(lazyListState.firstVisibleItemIndex) {
        if (lazyListState.firstVisibleItemIndex >= list.size - 1) {
            val firstPart = itemsListState.drop(lazyListState.firstVisibleItemIndex)
            val secondPart = itemsListState.take(lazyListState.firstVisibleItemIndex)
            itemsListState = firstPart + secondPart

            lazyListState.scrollToItem(0)
        }
    }

    LaunchedEffect(Unit) {
        autoScroll(lazyListState, scrollDx, delayBetweenScrollMs)
    }
}

private tailrec suspend fun autoScroll(
    lazyListState: LazyListState,
    scrollDx: Float,
    delayBetweenScrollMs: Long,
) {
    lazyListState.scroll(MutatePriority.PreventUserInput) {
        scrollBy(scrollDx)
    }
    delay(delayBetweenScrollMs)

    autoScroll(lazyListState, scrollDx, delayBetweenScrollMs)
}

private const val DELAY_BETWEEN_SCROLL_MS = 8L
private const val SCROLL_DX = 1f
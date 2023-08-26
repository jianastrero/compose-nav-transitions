package dev.jianastrero.compose_nav_transition

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect

internal object NavTransitions {

    var hostOffset = Offset.Zero
    private var _screenSharedElements: Map<String, Map<String, Rect>> by mutableStateOf(emptyMap())

    val screenSharedElements: Map<String, Map<String, Rect>>
        get() = _screenSharedElements

    fun addSharedElement(route: String, item: Pair<String, Rect>) {
        val routeSharedElements = (_screenSharedElements[route] ?: emptyMap()) + item
        _screenSharedElements = _screenSharedElements + (route to routeSharedElements)
    }

    fun keysFor(firstRoute: String, secondRoute: String): Collection<String> {
        val firstKeys = screenSharedElements[firstRoute]?.keys ?: emptySet()
        val secondKeys = screenSharedElements[secondRoute]?.keys ?: emptySet()
        return firstKeys.intersect(secondKeys)
    }
}

package dev.jianastrero.compose_nav_transition.navigation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import dev.jianastrero.compose_nav_transition.NavTransitions

class NavTransitionScope(
    internal val route: String
) {
    internal var previousRoute: String by mutableStateOf("")

    fun Modifier.sharedElement(tag: String): Modifier = onGloballyPositioned {
        val rect = with(it.positionInRoot()) {
            Rect(
                left = x,
                top = y,
                right = (x + it.size.width),
                bottom = (y + it.size.height)
            )
        }
        NavTransitions.addSharedElement(route, tag to rect)
    }
}

package dev.jianastrero.compose_nav_transition.modifier

import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import dev.jianastrero.compose_nav_transition.NavTransition

fun Modifier.sharedElement(tag: String): Modifier = onGloballyPositioned {
    val position = it.positionInRoot()
    NavTransition.addToCurrentElement(
        tag,
        Rect(
            left = position.x,
            top = position.y,
            right = (position.x + it.size.width),
            bottom = (position.y + it.size.height)
        )
    )
}

package dev.jianastrero.compose_nav_transition.animation

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.DpRect
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.height
import androidx.compose.ui.unit.width
import androidx.compose.ui.zIndex
import dev.jianastrero.compose_nav_transition.NavTransition


@Composable
fun NavTransitionContainer(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {

    Box(modifier = modifier) {
        content()
        TransitionAnimations()
    }
}

@Composable
private fun TransitionAnimations() {
    val density = LocalDensity.current
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background.copy(alpha = 0.2f))
        // TODO: Consume clicks when animation
    ) {
        Log.d("JIANDDEBUG", "NavTransition.currentElements.size: ${NavTransition.currentElements.size}")
        NavTransition.currentElements.forEach { (tag, rect) ->
            with(density) {
                val dpRect = DpRect(
                    left = rect.left.toDp(),
                    top = rect.top.toDp(),
                    right = rect.right.toDp(),
                    bottom = rect.bottom.toDp()
                )
                android.util.Log.d("JIANDDEBUG", "dpRect: ${dpRect}")
                Spacer(
                    modifier = androidx.compose.ui.Modifier
                        .offset(dpRect.left, dpRect.top)
                        .size(dpRect.width, dpRect.height)
                        .background(androidx.compose.ui.graphics.Color.Red.copy(alpha = 0.5f))
                )
            }
        }
    }
}

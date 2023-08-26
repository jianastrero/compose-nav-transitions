package dev.jianastrero.compose_nav_transition.animation

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpRect
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.height
import androidx.compose.ui.unit.width
import dev.jianastrero.compose_nav_transition.NavTransitions
import dev.jianastrero.compose_nav_transition.navigation.NavTransitionScope

@Composable
fun NavTransitionScope.NavTransitionContainer(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(modifier = modifier) {
        content()
        TransitionAnimations()
    }
}

@Composable
private fun NavTransitionScope.TransitionAnimations() {
    val density = LocalDensity.current

    var animate by remember { mutableStateOf(false) }
    val tags by remember {
        derivedStateOf {
            NavTransitions.keysFor(route, previousRoute)
        }
    }

    val animationProgress by animateFloatAsState(
        targetValue = if (animate) 1f else 0f,
        label = "all animations",
        animationSpec = tween(600)
    )

    val isAnimating by remember {
        derivedStateOf {
            animate || animationProgress < 1f
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background.copy(alpha = 0.5f))
    ) {
        tags.forEach {
            val startRect = NavTransitions.screenSharedElements[previousRoute]
                ?.get(it)
                ?.toDpRect(density)
                ?: DpRect.Zero
            val endRect = NavTransitions.screenSharedElements[route]
                ?.get(it)
                ?.toDpRect(density)
                ?: startRect
            val rect = startRect.lerp(endRect, animationProgress)
            Spacer(
                modifier = Modifier
                    .offset(rect.left, rect.top)
                    .size(rect.width, rect.height)
                    .background(Color.Red.copy(alpha = 0.5f))
            )
        }
    }

    LaunchedEffect(route, previousRoute) {
        if (isAnimating) {
            animate = true
        }
    }
}

private fun Dp.lerp(other: Dp, percent: Float): Dp = (this + ((other - this) * percent))

private fun DpRect.lerp(other: DpRect, percent: Float) = DpRect(
    left = left.lerp(other.left, percent),
    top = top.lerp(other.top, percent),
    right = right.lerp(other.right, percent),
    bottom = bottom.lerp(other.bottom, percent)
)

private fun Rect.toDpRect(density: Density): DpRect = with(density) {
    DpRect(
        left = left.toDp(),
        top = top.toDp(),
        right = right.toDp(),
        bottom = bottom.toDp()
    )
}

private val DpRect.Companion.Zero: DpRect
    get() = DpRect(0.dp, 0.dp, 0.dp, 0.dp)

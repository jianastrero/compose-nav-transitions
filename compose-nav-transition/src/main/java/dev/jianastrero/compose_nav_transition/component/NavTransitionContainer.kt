/*
 * MIT License
 *
 * Copyright (c) 2023 Jian James Astrero
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package dev.jianastrero.compose_nav_transition.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
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
    var animateVisibility by remember { mutableStateOf(false) }
    var visible by remember { mutableStateOf(false) }
    val elements by remember(route, previousRoute, NavTransitions.screenSharedElements) {
        derivedStateOf {
            NavTransitions.keysFor(route, previousRoute).map {
                val start = NavTransitions.screenSharedElements[previousRoute]
                    ?.get(it)
                val end = NavTransitions.screenSharedElements[route]
                    ?.get(it)
                val startRect = start?.first?.toDpRect(density) ?: DpRect.Zero
                val endRect = end?.first?.toDpRect(density) ?: DpRect.Zero
                (startRect to start?.second) to (endRect to end?.second)
            }
        }
    }

    val animationProgress by animateFloatAsState(
        targetValue = if (animate) 1f else 0f,
        label = "all animations",
        animationSpec = tween(NavTransitions.transitionDuration),
        finishedListener = {
            visible = false
        }
    )
    val visibilityProgress by animateFloatAsState(
        targetValue = if (animateVisibility) 0f else 1f,
        label = "visibility animation",
        animationSpec = tween(durationMillis = NavTransitions.transitionDuration)
    )

    if (visibilityProgress > 0f || visible) {
        Box(modifier = Modifier.fillMaxSize()) {
            Spacer(
                modifier = Modifier
                    .alpha(visibilityProgress)
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
            )
            elements.forEach { (start, end) ->
                val rect = start.first.lerp(end.first, animationProgress)
                Box(
                    modifier = Modifier
                        .offset(rect.left, rect.top)
                        .size(rect.width, rect.height)
                ) {
                    when (val element = end.second) {
                        null -> Spacer(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.LightGray)
                        )
                        else -> element.Composable()
                    }
                }
            }
        }
    }

    LaunchedEffect(route, previousRoute) {
        if (!animate) {
            animate = true
            visible = true && route != previousRoute && route.isNotBlank() && previousRoute.isNotBlank()
            animateVisibility = true
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

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
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpRect
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.height
import androidx.compose.ui.unit.width
import androidx.compose.ui.zIndex
import dev.jianastrero.compose_nav_transition.element.Element
import dev.jianastrero.compose_nav_transition.element.IconElement
import dev.jianastrero.compose_nav_transition.element.ImageElement
import dev.jianastrero.compose_nav_transition.element.TextElement
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
    var animate by remember { mutableStateOf(false) }
    var visible by remember { mutableStateOf(false) }
    val sharedElements = rememberSharedElements()

    val animationProgress by animateFloatAsState(
        targetValue = if (animate) 1f else 0f,
        label = "all animations",
        animationSpec = tween(transitionDuration),
        finishedListener = {
            visible = false
        }
    )
    val originalElementVisibilityProgress by animateFloatAsState(
        targetValue = if (animate) 1f else 0f,
        label = "original element visibility",
        animationSpec = tween(
            durationMillis = 50,
            delayMillis = transitionDuration - 50
        )
    )

    if (visible) {
        Box(
            modifier = Modifier
                .zIndex(Float.MAX_VALUE)
                .fillMaxSize()
        ) {
            Backdrop(animationProgress)
            sharedElements.forEach { elementPair ->
                elementPair.Element(animationProgress)
            }
        }
    }

    LaunchedEffect(sharedElements) {
        if (!animate && sharedElements.isNotEmpty()) {
            animate = true
            visible = true
        }
    }

    LaunchedEffect(originalElementVisibilityProgress) {
        alphaMap = elements.associate { element ->
            element.tag to if (previousElements.firstOrNull { it.tag == element.tag } != null) {
                originalElementVisibilityProgress
            } else {
                1f
            }
        }
    }
}

@Composable
private fun NavTransitionScope.rememberSharedElements(): List<Pair<Element, Element>> {
    val sharedElements by remember(previousElements) {
        derivedStateOf {
            previousElements.mapNotNull { element ->
                elements.firstOrNull { it.tag == element.tag }?.let { currentElement ->
                    element to currentElement
                }
            }
        }
    }

    return sharedElements
}

@Composable
private fun Backdrop(animationProgress: Float) {
    Spacer(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        tryAwaitRelease()
                    }
                )
            }
            .alpha(1f - animationProgress)
            .background(MaterialTheme.colorScheme.background)
    )
}

@Composable
private fun Pair<Element, Element>.Element(animationProgress: Float) {
    val (startElement, endElement) = this
    val rect = startElement.rect.lerp(endElement.rect, animationProgress)

    Box(
        modifier = Modifier
            .offset(rect.left, rect.top)
            .size(rect.width, rect.height)
    ) {
        when (endElement) {
            is TextElement,
            is ImageElement,
            is IconElement -> endElement.Composable()
            else -> Spacer(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.LightGray)
            )
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

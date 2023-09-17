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
 */

package dev.jianastrero.compose_nav_transition.composable

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.DpRect
import androidx.compose.ui.unit.height
import androidx.compose.ui.unit.width
import dev.jianastrero.compose_nav_transition.element.Element
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun NavTransition(
    elements: List<Element>,
    modifier: Modifier = Modifier,
    transitionDuration: Duration = 600.milliseconds,
    content: @Composable BoxScope.() -> Unit
) {
    var animate by rememberSaveable { mutableStateOf(false) }
    var originalVisible by rememberSaveable { mutableStateOf(false) }
    val animationProgress by animateFloatAsState(
        targetValue = if (animate) 1f else 0f,
        label = "animationProgress",
        animationSpec = tween(transitionDuration.inWholeMilliseconds.toInt()),
        finishedListener = {
            originalVisible = true
        }
    )
    val originalVisibleProgress by animateFloatAsState(
        targetValue = if (originalVisible) 1f else 0f,
        label = "originalVisibleProgress"
    )
    val sharedRects by remember(elements, animationProgress) {
        derivedStateOf {
            elements.mapNotNull { element ->
                element.transitionDpRect(animationProgress)?.let { it to element }
            }
        }
    }

    Box(modifier = modifier) {
        content()
        if (originalVisibleProgress != 1f) {
            sharedRects.map { (rect, element) ->
                element.imageData?.let { imageData ->
                    Image(
                        painter = imageData.painter,
                        contentDescription = "Transition element",
                        contentScale = imageData.contentScale,
                        modifier = Modifier
                            .absoluteOffset(x = rect.left, y = rect.top)
                            .size(rect.width, rect.height)
                    )
                } ?: run {
                    Spacer(
                        modifier = Modifier
                            .absoluteOffset(x = rect.left, y = rect.top)
                            .size(rect.width, rect.height)
                            .background(color = Color.LightGray.copy(0.6f), shape = MaterialTheme.shapes.small)
                    )
                }
            }
        }
    }

    LaunchedEffect(animate) {
        if (!animate) {
            animate = true
        }
    }

    LaunchedEffect(originalVisibleProgress) {
        elements.forEach {
            it.alpha = originalVisibleProgress
        }
    }
}

fun Modifier.sharedElement(element: Element): Modifier = composed {
    val density = LocalDensity.current

    onGloballyPositioned {
        element.rect = with(density) {
            val position = it.positionInRoot()
            DpRect(
                left = position.x.toDp(),
                top = position.y.toDp(),
                right = (position.x + it.size.width).toDp(),
                bottom = (position.y + it.size.height).toDp(),
            )
        }
    }
        .alpha(element.alpha)
}

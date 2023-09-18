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

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpRect
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.height
import androidx.compose.ui.unit.width
import dev.jianastrero.compose_nav_transition.element.Element

@Composable
fun NavTransition(
    elements: List<Element>,
    modifier: Modifier = Modifier,
    animationSpec: AnimationSpec<Float> = tween(1600),
    content: @Composable BoxScope.() -> Unit
) {
    var animate by rememberSaveable { mutableStateOf(false) }
    var originalVisible by rememberSaveable { mutableStateOf(false) }
    val animationProgress by animateFloatAsState(
        targetValue = if (animate) 1f else 0f,
        label = "animationProgress",
        animationSpec = animationSpec,
        finishedListener = {
            originalVisible = true
        }
    )
    val originalVisibleProgress by animateFloatAsState(
        targetValue = if (originalVisible) 1f else 0f,
        label = "originalVisibleProgress",
        animationSpec = spring()
    )
    val sharedRects by remember(elements, animationProgress) {
        derivedStateOf {
            elements.mapNotNull { element ->
                if (element.textData == null || element.imageData == null) return@mapNotNull null
                element.transitionDpRect(animationProgress)
            }
        }
    }
    val sharedTexts by remember {
        derivedStateOf {
            elements.mapNotNull { element ->
                val textData = element.textData ?: return@mapNotNull null
                val rect = element.transitionDpRect(animationProgress) ?: return@mapNotNull null
                val fontSize = element.textData?.fontSize?.transitionTextUnit(
                    element.fromTextData?.fontSize,
                    animationProgress
                ) ?: return@mapNotNull null
                val letterSpacing = element.textData?.letterSpacing?.transitionTextUnit(
                    element.fromTextData?.letterSpacing,
                    animationProgress
                ) ?: return@mapNotNull null
                rect to textData.copy(fontSize = fontSize, letterSpacing = letterSpacing)
            }
        }
    }

    Box(modifier = modifier) {
        content()
        if (originalVisibleProgress != 1f) {
            sharedRects.map { rect ->
                Spacer(
                    modifier = Modifier
                        .absoluteOffset(x = rect.left, y = rect.top)
                        .size(rect.width, rect.height)
                        .background(color = Color.LightGray.copy(0.6f), shape = MaterialTheme.shapes.small)
                )
            }
            sharedTexts.map { (rect, textData) ->
                Text(
                    text = textData.text,
                    color = textData.textColor,
                    fontSize = textData.fontSize,
                    fontStyle = textData.fontStyle,
                    fontWeight = textData.fontWeight,
                    fontFamily = textData.fontFamily,
                    letterSpacing = textData.letterSpacing,
                    textDecoration = textData.textDecoration,
                    textAlign = textData.textAlign,
                    lineHeight = textData.lineHeight,
                    overflow = textData.overflow,
                    softWrap = textData.softWrap,
                    maxLines = textData.maxLines,
                    style = textData.style ?: LocalTextStyle.current,
                    modifier = Modifier
                        .absoluteOffset(x = rect.left, y = rect.top)
                        .size(rect.width, rect.height)
                )
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

internal fun Element.transitionDpRect(fraction: Float): DpRect? = fromRect?.lerpRect(rect, fraction)

internal fun TextUnit?.transitionTextUnit(from: TextUnit?, fraction: Float): TextUnit? =
    from?.lerp(
        this ?: TextUnit.Unspecified,
        fraction
    )

internal fun DpRect.lerpRect(stop: DpRect, fraction: Float): DpRect {
    val left = left.lerp(stop.left, fraction)
    val top = top.lerp(stop.top, fraction)
    val right = right.lerp(stop.right, fraction)
    val bottom = bottom.lerp(stop.bottom, fraction)

    return DpRect(left, top, right, bottom)
}

internal fun Dp.lerp(
    stop: Dp,
    fraction: Float
): Dp = Dp(value + ((stop.value - value) * fraction))

internal fun TextUnit.lerp(stop: TextUnit, fraction: Float): TextUnit {
    return when {
        this == TextUnit.Unspecified -> stop
        stop == TextUnit.Unspecified -> this
        else -> TextUnit(value + ((stop.value - value) * fraction), type)
    }
}

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

package dev.jianastrero.compose_nav_transition

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.navigation.NavBackStackEntry
import dev.jianastrero.compose_nav_transition.element.Element
import dev.jianastrero.compose_nav_transition.element.EmptyTransitionElements
import dev.jianastrero.compose_nav_transition.element.TransitionElements
import dev.jianastrero.compose_nav_transition.logger.print
import kotlinx.coroutines.flow.MutableStateFlow

internal object NavTransitionManager {

    val transitionElements = MutableStateFlow(EmptyTransitionElements)
    val transitionDuration = MutableStateFlow(600)

    suspend fun push(elements: List<Element>) {
        val stack = transitionElements.value
        transitionElements.emit(TransitionElements(currentElements = elements, previousElements = stack.currentElements))
    }

    @Composable
    fun Transition(currentBackStackEntry: NavBackStackEntry?) {
        val elements by transitionElements.collectAsState()
        val transitionDuration by transitionDuration.collectAsState()
        var animate by rememberSaveable(currentBackStackEntry) { mutableStateOf(false) }

        val animateProgress by animateFloatAsState(
            targetValue = if (animate) 0f else 1f,
            label = "animateProgress",
            animationSpec = if (animate) tween(transitionDuration) else tween(0)
        )

        Box(
            modifier = Modifier
                .alpha(animateProgress)
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background)
        ) {
        }

        LaunchedEffect(animate, currentBackStackEntry) {
            animate.print("animate")
            if (!animate) {
                animate = true
            }
        }

        LaunchedEffect(animateProgress) {
            animateProgress.print("animateProgress")
        }
    }
}

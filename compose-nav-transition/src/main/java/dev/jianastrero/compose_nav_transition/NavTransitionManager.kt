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

package dev.jianastrero.compose_nav_transition

import androidx.compose.ui.geometry.Offset
import dev.jianastrero.compose_nav_transition.element.Element
import dev.jianastrero.compose_nav_transition.element.EmptyTransitionElement
import dev.jianastrero.compose_nav_transition.element.TransitionElement
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

internal object NavTransitionManager {

    private val defaultScope = CoroutineScope(Dispatchers.Default)
    private val _transitionElements = MutableStateFlow(EmptyTransitionElement)

    var hostOffset = Offset.Zero

    val transitionElements: StateFlow<TransitionElement> = _transitionElements

    suspend fun push(elements: List<Element>) {
        val stack = _transitionElements.value
        _transitionElements.emit(TransitionElement(currentElements = elements, previousElements = stack.currentElements))
    }
}

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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map

internal object NavTransitionManager {
    var hostOffset = Offset.Zero
    private val transitionElementsStack = MutableStateFlow(ArrayDeque<List<Element>>())

    val transitionElements = transitionElementsStack.map {
        val currentElements = it.lastOrNull()
        val previousElements = if (it.size == 1) null else it.firstOrNull()
        currentElements to previousElements
    }

    suspend fun push(elements: List<Element>) {
        val stack = transitionElementsStack.value
        stack.addLast(elements)
        stack.removeFirst()
        transitionElementsStack.emit(stack)
    }
}

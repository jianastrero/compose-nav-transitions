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

package dev.jianastrero.compose_nav_transition.navigation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import dev.jianastrero.compose_nav_transition.NavTransitions
import dev.jianastrero.compose_nav_transition.element.Element

class NavTransitionScope(
    internal val route: String
) {
    internal var previousRoute: String by mutableStateOf("")
    internal var elements: Map<String, Element> by mutableStateOf(emptyMap())
    internal var alphaMap: Map<String, Float> by mutableStateOf(emptyMap())

    fun Modifier.sharedElement(tag: String): Modifier = onGloballyPositioned {
        if (elements.isEmpty() || !elements.containsKey(tag)) {
            val rect = with(it.positionInRoot()) {
                Rect(
                    left = x,
                    top = y,
                    right = (x + it.size.width),
                    bottom = (y + it.size.height)
                )
            }
            val element = object : Element(tag) {}
            elements = elements + (tag to element)
            NavTransitions.addSharedElement(route, tag to (rect to element))
        }
    }.alpha(alphaMap[tag] ?: 1f)

    fun Modifier.sharedElement(element: Element): Modifier = onGloballyPositioned {
        if (elements.isEmpty() || !elements.containsKey(element.tag)) {
            elements = elements + (element.tag to element)
            val rect = with(it.positionInRoot()) {
                Rect(
                    left = x,
                    top = y,
                    right = (x + it.size.width),
                    bottom = (y + it.size.height)
                )
            }
            NavTransitions.addSharedElement(route, element.tag to (rect to element))
        }
    }.alpha(alphaMap[element.tag] ?: 1f)

    companion object {
        val Preview = NavTransitionScope("")
    }
}

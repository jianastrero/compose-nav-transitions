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

package dev.jianastrero.compose_nav_transition.navigation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.DpRect
import androidx.navigation.NavOptionsBuilder
import dev.jianastrero.compose_nav_transition.element.Element

class NavTransitionScope {
    internal var previousElements: Collection<Element> by mutableStateOf(emptyList())
    internal var elements: Collection<Element> by mutableStateOf(emptyList())
    internal var alphaMap: Map<String, Float> by mutableStateOf(emptyMap())
    internal var transitionDuration by mutableIntStateOf(0)
    internal var passedElements: Collection<Element> by mutableStateOf(emptyList())

    fun Modifier.sharedElement(tag: String): Modifier = composed {
        val density = LocalDensity.current

        onGloballyPositioned {
            addElement(density, tag, null, it)
        }.alpha(alphaMap[tag] ?: 1f)
    }

    fun Modifier.sharedElement(element: Element): Modifier = composed {
        val density = LocalDensity.current

        onGloballyPositioned {
            addElement(density, element.tag, element, it)
        }.alpha(alphaMap[element.tag] ?: 1f)
    }

    fun NavOptionsBuilder.sharedElements(vararg sharedElements: Element) {
        passedElements = sharedElements.toList()
    }

    private fun addElement(
        density: Density,
        tag: String,
        element: Element?,
        layoutCoordinates: LayoutCoordinates
    ) {
        val newElement = element ?: object : Element(tag) {}
        with(density) {
            newElement.rect = with(layoutCoordinates.positionInRoot()) {
                val xDp = x.toDp()
                val yDp = y.toDp()

                DpRect(
                    left = xDp,
                    top = yDp,
                    right = (xDp + layoutCoordinates.size.width.toDp()),
                    bottom = (yDp + layoutCoordinates.size.height.toDp())
                )
            }
            elements += newElement
        }
    }

    internal fun resetElements(
        elements: Collection<Element> = emptyList(),
        previousElements: Collection<Element> = emptyList(),
        passedElements: Collection<Element> = emptyList()
    ) {
        this.elements = elements
        this.previousElements = previousElements
        this.passedElements = passedElements
    }

    companion object {
        val Preview = NavTransitionScope()
    }
}

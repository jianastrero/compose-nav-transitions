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

import androidx.compose.runtime.saveable.listSaver
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.DpRect
import androidx.navigation.NavOptionsBuilder
import dev.jianastrero.compose_nav_transition.element.Element
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class NavTransitionScope(initialElements: List<Element> = emptyList()) {

    internal var elements = MutableStateFlow(initialElements)

    fun Modifier.sharedElement(tag: String): Modifier = composed {
        val density = LocalDensity.current

        onGloballyPositioned {
            addElement(density, tag, null, it)
        }
    }

    fun Modifier.sharedElement(element: Element): Modifier = composed {
        val density = LocalDensity.current

        onGloballyPositioned {
            addElement(density, element.tag, element, it)
        }
    }

    fun NavOptionsBuilder.sharedElements(vararg sharedElements: Element) {

    }

    private fun Modifier.addElement(
        density: Density,
        tag: String,
        element: Element?,
        layoutCoordinates: LayoutCoordinates
    ) {
        elements.update {
            val newElement = element ?: object : Element(tag) {}
            newElement.modifier.update { this@addElement }
            newElement.rect.update {
                with(density) {
                    with(layoutCoordinates.positionInRoot()) {
                        val xDp = x.toDp()
                        val yDp = y.toDp()

                        DpRect(
                            left = xDp,
                            top = yDp,
                            right = (xDp + layoutCoordinates.size.width.toDp()),
                            bottom = (yDp + layoutCoordinates.size.height.toDp())
                        )
                    }
                }
            }
            it + newElement
        }
    }

    companion object {
        val Saver = listSaver(
            save = {
                it.elements.value
            },
            restore = {
                val elements = it.firstOrNull() ?: emptyList<Element>()
                NavTransitionScope(elements)
            }
        )
    }
}

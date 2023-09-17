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

package dev.jianastrero.compose_nav_transition.element

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpRect
import androidx.compose.ui.unit.dp

class Element internal constructor() {
    internal var rect: DpRect by mutableStateOf(DpRect(0.dp, 0.dp, 0.dp, 0.dp))
    private var fromRect: DpRect? by mutableStateOf(null)
    internal var alpha by mutableFloatStateOf(1f)

    fun connect(element: Element) {
        if (element == None) return
        fromRect = element.rect.copy()
    }

    internal fun transitionDpRect(fraction: Float): DpRect? = fromRect?.let { lerpRect(it, rect, fraction) }

    private fun lerpRect(start: DpRect, stop: DpRect, fraction: Float): DpRect {
        val left = lerp(start.left, stop.left, fraction)
        val top = lerp(start.top, stop.top, fraction)
        val right = lerp(start.right, stop.right, fraction)
        val bottom = lerp(start.bottom, stop.bottom, fraction)

        return DpRect(left, top, right, bottom)
    }

    private fun lerp(
        start: Dp,
        stop: Dp,
        fraction: Float
    ): Dp = Dp(start.value + ((stop.value - start.value) * fraction))

    companion object {
        val None = Element()
    }
}

@Composable
fun rememberElements(count: Int): Array<Element> {
    return rememberSaveable(count) { Array(count) { Element() } }
}

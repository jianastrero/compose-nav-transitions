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

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect

internal object NavTransitions {

    var hostOffset = Offset.Zero
    private var _screenSharedElements: Map<String, Map<String, Rect>> by mutableStateOf(emptyMap())

    val screenSharedElements: Map<String, Map<String, Rect>>
        get() = _screenSharedElements

    fun addSharedElement(route: String, item: Pair<String, Rect>) {
        val routeSharedElements = (_screenSharedElements[route] ?: emptyMap()) + item
        _screenSharedElements = _screenSharedElements + (route to routeSharedElements)
    }

    fun keysFor(firstRoute: String, secondRoute: String): Collection<String> {
        val firstKeys = screenSharedElements[firstRoute]?.keys ?: emptySet()
        val secondKeys = screenSharedElements[secondRoute]?.keys ?: emptySet()
        return firstKeys.intersect(secondKeys)
    }
}

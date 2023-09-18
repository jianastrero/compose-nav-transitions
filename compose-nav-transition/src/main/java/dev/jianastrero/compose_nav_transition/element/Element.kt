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

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.DpRect
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp

class Element internal constructor() {
    internal var alpha by mutableFloatStateOf(1f)
    internal var rect: DpRect by mutableStateOf(DpRect(0.dp, 0.dp, 0.dp, 0.dp))
    internal var fromRect: DpRect? by mutableStateOf(null)
    internal var imageData: ImageData? by mutableStateOf(null)
    internal var textData: TextData? by mutableStateOf(null)
    internal var fromTextData: TextData? by mutableStateOf(null)

    fun connect(element: Element) {
        if (element == None) return
        fromRect = element.rect.copy()
        Log.d("JIANDDEBUG", "element.fromTextData: ${element.textData}")
        fromTextData = element.textData
    }

    fun with(painter: Painter): Painter = painter.also {
        imageData = imageData?.copy(painter = it) ?: ImageData(it)
    }

    fun with(contentScale: ContentScale): ContentScale = contentScale.also {
        imageData = imageData?.copy(contentScale = it)
    }

    fun with(text: String): String = text.also {
        textData = textData?.copy(text = it) ?: TextData(it)
    }

    fun with(textColor: Color): Color = textColor.also {
        textData = textData?.copy(textColor = it) ?: TextData(text = "", textColor = it)
    }

    fun withFontSize(fontSize: TextUnit): TextUnit = fontSize.also {
        textData = textData?.copy(fontSize = it) ?: TextData(text = "", fontSize = it)
    }

    fun with(fontStyle: FontStyle?): FontStyle? = fontStyle.also {
        textData = textData?.copy(fontStyle = it) ?: TextData(text = "", fontStyle = it)
    }

    fun with(fontWeight: FontWeight?): FontWeight? = fontWeight.also {
        textData = textData?.copy(fontWeight = it) ?: TextData(text = "", fontWeight = it)
    }

    fun with(fontFamily: FontFamily?): FontFamily? = fontFamily.also {
        textData = textData?.copy(fontFamily = it) ?: TextData(text = "", fontFamily = it)
    }

    fun withLetterSpacing(letterSpacing: TextUnit): TextUnit = letterSpacing.also {
        textData = textData?.copy(letterSpacing = it) ?: TextData(text = "", letterSpacing = it)
    }

    fun with(textDecoration: TextDecoration?): TextDecoration? = textDecoration.also {
        textData = textData?.copy(textDecoration = it) ?: TextData(text = "", textDecoration = it)
    }

    fun with(textAlign: TextAlign?): TextAlign? = textAlign.also {
        textData = textData?.copy(textAlign = it) ?: TextData(text = "", textAlign = it)
    }

    fun withLineHeight(lineHeight: TextUnit): TextUnit = lineHeight.also {
        textData = textData?.copy(lineHeight = it) ?: TextData(text = "", lineHeight = it)
    }

    fun with(overflow: TextOverflow): TextOverflow = overflow.also {
        textData = textData?.copy(overflow = it) ?: TextData(text = "", overflow = it)
    }

    fun with(softWrap: Boolean): Boolean = softWrap.also {
        textData = textData?.copy(softWrap = it) ?: TextData(text = "", softWrap = it)
    }

    fun with(maxLines: Int): Int = maxLines.also {
        textData = textData?.copy(maxLines = it) ?: TextData(text = "", maxLines = it)
    }

    fun with(style: TextStyle?): TextStyle? = style.also {
        textData = textData?.copy(style = it) ?: TextData(text = "", style = it)
    }

    companion object {
        val None = Element()
    }
}

data class ImageData(
    val painter: Painter,
    val contentScale: ContentScale = ContentScale.Fit
)

data class TextData(
    val text: String,
    val textColor: Color = Color.Unspecified,
    val fontSize: TextUnit = TextUnit.Unspecified,
    val fontStyle: FontStyle? = null,
    val fontWeight: FontWeight? = null,
    val fontFamily: FontFamily? = null,
    val letterSpacing: TextUnit = TextUnit.Unspecified,
    val textDecoration: TextDecoration? = null,
    val textAlign: TextAlign? = null,
    val lineHeight: TextUnit = TextUnit.Unspecified,
    val overflow: TextOverflow = TextOverflow.Clip,
    val softWrap: Boolean = true,
    val maxLines: Int = Int.MAX_VALUE,
    val style: TextStyle? = null
)

@Composable
fun rememberElements(count: Int): Array<Element> {
    return rememberSaveable(count) { Array(count) { Element() } }
}

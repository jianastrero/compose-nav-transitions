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

package dev.jianastrero.compose_nav_transition.element

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit

abstract class Element(
    internal val modifier: Modifier = Modifier
) {
    @Composable
    internal open fun Composable() {
        Spacer(modifier = modifier)
    }
}

class TextElement(
    internal val text: String,
    modifier: Modifier = Modifier,
    internal val fontSize: TextUnit = TextUnit.Unspecified,
    internal val fontWeight: FontWeight? = null,
    internal val fontStyle: FontStyle? = null,
    internal val fontFamily: FontFamily? = null,
    internal val letterSpacing: TextUnit = TextUnit.Unspecified,
    internal val textDecoration: TextDecoration? = null,
    internal val textAlign: TextAlign? = null,
    internal val lineHeight: TextUnit = TextUnit.Unspecified,
    internal val overflow: TextOverflow = TextOverflow.Clip,
    internal val softWrap: Boolean = true,
    internal val maxLines: Int = Int.MAX_VALUE,
    internal val onTextLayout: (TextLayoutResult) -> Unit = {},
    internal val style: TextStyle = TextStyle.Default
) : Element(modifier) {
    @Composable
    override fun Composable() {
        Text(
            text = text,
            fontSize = fontSize,
            fontWeight = fontWeight,
            fontStyle = fontStyle,
            fontFamily = fontFamily,
            letterSpacing = letterSpacing,
            textDecoration = textDecoration,
            textAlign = textAlign,
            lineHeight = lineHeight,
            overflow = overflow,
            softWrap = softWrap,
            maxLines = maxLines,
            onTextLayout = onTextLayout,
            style = style,
            modifier = modifier
        )
    }
}

class ImageElement(
    internal val painter: Painter,
    modifier: Modifier = Modifier,
    internal val contentDescription: String? = null,
    internal val contentScale: ContentScale = ContentScale.Fit,
) : Element(modifier) {
    @Composable
    override fun Composable() {
        Image(
            painter = painter,
            contentDescription = contentDescription,
            contentScale = contentScale,
            modifier = modifier
        )
    }
}

class ImageVectorElement(
    internal val imageVector: ImageVector,
    modifier: Modifier = Modifier,
    internal val contentDescription: String? = null,
) : Element(modifier) {
    @Composable
    override fun Composable() {
        Image(
            imageVector = imageVector,
            contentDescription = contentDescription,
            modifier = modifier
        )
    }
}

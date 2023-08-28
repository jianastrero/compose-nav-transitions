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

package dev.jianastrero.compose_nav_transition.example.shared_elements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import dev.jianastrero.compose_nav_transition.element.ImageElement
import dev.jianastrero.compose_nav_transition.element.ImageVectorElement
import dev.jianastrero.compose_nav_transition.element.TextElement


object NotificationSharedElements  {
    const val TAG_ICON = "notification_icon"
    const val TAG_ICON_CLOSE = "notification_icon_close"
    const val TAG_ITEM_IMAGE = "notification_item_image"
    const val TAG_ITEM_TEXT = "notification_item_text"
    const val TAG_ITEM_DESCRIPTION = "notification_item_description"

    val notificationIconElement = ImageVectorElement(
        imageVector = Icons.Outlined.Notifications,
        modifier = Modifier
            .size(56.dp)
            .padding(16.dp)
    )

    val closeIconElement = ImageVectorElement(
        imageVector = Icons.Outlined.Close,
        modifier = Modifier
            .size(56.dp)
            .padding(16.dp)
    )

    @Composable
    fun itemImageElement(id: Int) = ImageElement(
        painter = painterResource(id = id),
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    )

    @Composable
    fun itemTextElement(
        text: String,
        style: TextStyle
    ) = TextElement(
        text = text,
        textAlign = TextAlign.Center,
        style = style
    )

    @Composable
    fun itemDescriptionElement(
        text: String,
        style: TextStyle
    ) = TextElement(
        text = text,
        overflow = TextOverflow.Ellipsis,
        style = style
    )
}

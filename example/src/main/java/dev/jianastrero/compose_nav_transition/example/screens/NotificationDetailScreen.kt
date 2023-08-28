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

package dev.jianastrero.compose_nav_transition.example.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.jianastrero.compose_nav_transition.example.Constants
import dev.jianastrero.compose_nav_transition.example.R
import dev.jianastrero.compose_nav_transition.example.shared_elements.NotificationSharedElements
import dev.jianastrero.compose_nav_transition.navigation.NavTransitionScope

@Composable
fun NavTransitionScope.NotificationDetailScreen(
    id: Int,
    back: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = "Notification Item",
                    modifier = Modifier
                        .sharedElement(
                            tag = NotificationSharedElements.TAG_ITEM_IMAGE,
                            element = NotificationSharedElements.itemImageElement(R.drawable.ic_launcher_foreground)
                        )
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .background(Color.Black)
                )
            }
            item {
                Text(
                    text = "Notification $id",
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(12.dp)
                        .sharedElement(
                            tag = NotificationSharedElements.TAG_ITEM_TEXT,
                            element = NotificationSharedElements.itemTextElement(
                                "Notification $id",
                                style = MaterialTheme.typography.headlineMedium
                            )
                        )
                )
            }
            item {
                Text(
                    text = "($id) ${Constants.DUMMY_TEXT}",
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp)
                        .sharedElement(
                            tag = NotificationSharedElements.TAG_ITEM_DESCRIPTION,
                            element = NotificationSharedElements.itemDescriptionElement(
                                "($id) ${Constants.DUMMY_TEXT}",
                                style = MaterialTheme.typography.labelSmall
                            )
                        )
                )
            }
        }
        Icon(
            imageVector = Icons.Outlined.Close,
            contentDescription = "Close",
            tint = Color.White,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .clip(CircleShape)
                .clickable(onClick = back)
                .sharedElement(
                    tag = NotificationSharedElements.TAG_ICON_CLOSE,
                    element = NotificationSharedElements.closeIconElement(tint = Color.White)
                )
                .size(56.dp)
                .padding(16.dp)
        )
    }
}

@Preview
@Composable
private fun NotificationDetailScreenPreview() {
    NavTransitionScope.Preview.NotificationDetailScreen(
        id = 2,
        back = {},
        modifier = Modifier.fillMaxSize()
    )
}

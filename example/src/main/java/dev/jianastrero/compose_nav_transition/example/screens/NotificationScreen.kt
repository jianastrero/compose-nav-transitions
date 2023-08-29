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
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Notifications
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.jianastrero.compose_nav_transition.example.Constants
import dev.jianastrero.compose_nav_transition.example.R
import dev.jianastrero.compose_nav_transition.example.shared_elements.NotificationSharedElements
import dev.jianastrero.compose_nav_transition.navigation.NavTransitionScope

@Composable
fun NavTransitionScope.NotificationScreen(
    navigate: (String) -> Unit,
    back: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Header(back = back, modifier = Modifier.fillMaxWidth())

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(12.dp)
        ) {
            items(24) {
                NotificationItem(
                    title = "Notification $it",
                    message = "($it) ${Constants.DUMMY_TEXT}",
                    image = R.drawable.ic_launcher_foreground,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { navigate("notification_detail/$it") }
                )
            }
        }
    }
}

@Composable
private fun NavTransitionScope.Header(
    back: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        Text(
            text = "Notifications",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
        )
        Icon(
            imageVector = Icons.Outlined.Notifications,
            contentDescription = "Notifications",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .sharedElement(
                    element = NotificationSharedElements.notificationIconElement(MaterialTheme.colorScheme.primary)
                )
                .clip(CircleShape)
                .size(56.dp)
                .padding(16.dp)
        )
        Icon(
            imageVector = Icons.Outlined.Close,
            contentDescription = "Close",
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .sharedElement(
                    element = NotificationSharedElements.closeIconElement(tint = Color.Black)
                )
                .clip(CircleShape)
                .size(56.dp)
                .clickable(onClick = back)
                .padding(16.dp)
        )
    }
}

@Composable
private fun NavTransitionScope.NotificationItem(
    title: String,
    message: String,
    image: Int,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = image),
                contentDescription = "Notification Item",
                modifier = Modifier
                    .sharedElement(
                        element = NotificationSharedElements.itemImageElement(image)
                    )
                    .size(32.dp)
                    .border(1.dp, Color.LightGray)
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 12.dp)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier
                        .fillMaxWidth()
                        .sharedElement(
                            element = NotificationSharedElements.itemTextElement(
                                title,
                                style = MaterialTheme.typography.headlineSmall
                            )
                        )
                )
                Text(
                    text = message,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp)
                        .sharedElement(
                            element = NotificationSharedElements.itemDescriptionElement(
                                message,
                                style = MaterialTheme.typography.labelSmall
                            )
                        )
                )
            }
        }
        Spacer(
            modifier = Modifier
                .padding(top = 4.dp, start = 12.dp, end = 12.dp)
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.LightGray)
        )
    }
}

@Preview
@Composable
private fun NotificationScreenPreview() {
    NavTransitionScope.Preview.NotificationScreen(
        navigate = {},
        back = {},
        modifier = Modifier.fillMaxSize()
    )
}

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

package dev.jianastrero.compose_nav_transition.example.nav_graph

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.navArgument
import dev.jianastrero.compose_nav_transition.element.Element
import dev.jianastrero.compose_nav_transition.example.screens.MainScreen
import dev.jianastrero.compose_nav_transition.example.screens.NotificationDetailScreen
import dev.jianastrero.compose_nav_transition.example.screens.NotificationScreen
import dev.jianastrero.compose_nav_transition.navigation.NavTransitionGraphBuilder
import dev.jianastrero.compose_nav_transition.navigation.NavTransitionScope
import dev.jianastrero.compose_nav_transition.navigation.transitionComposable

fun NavTransitionGraphBuilder.MainNavGraph(
    navigate: NavTransitionScope.(String, sharedElements: Collection<Element>?) -> Unit,
    back: () -> Unit,
    modifier: Modifier = Modifier
) {
    transitionComposable("home") {
        MainScreen(
            navigate = navigate,
            modifier = modifier
        )
    }
    transitionComposable("notifications") {
        NotificationScreen(
            navigate = navigate,
            back = back,
            modifier = modifier
        )
    }
    transitionComposable(
        "notification_detail/{id}",
        arguments = listOf(
            navArgument("id") {
                type = NavType.IntType
            }
        )
    ) {
        val id = it.arguments?.getInt("id") ?: 0

        NotificationDetailScreen(
            id = id,
            back = back,
            modifier = Modifier.fillMaxSize()
        )
    }
}

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

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import dev.jianastrero.compose_nav_transition.NavTransitionManager
import dev.jianastrero.compose_nav_transition.logger.print

@Composable
fun NavTransitionHost(
    navController: NavHostController,
    startDestination: String,
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.Center,
    route: String? = null,
    transitionDuration: Int = 600,
    builder: NavTransitionGraphBuilder.() -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val currentBackStackEntry by navController.currentBackStackEntryAsState()

    val enterTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition) =
        remember(transitionDuration) {
            { fadeIn(tween(transitionDuration)) }
        }
    val exitTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition) =
        remember(transitionDuration) {
            { fadeOut(tween(transitionDuration)) }
        }

    val navGraph = remember(navController, startDestination, route, builder) {
        NavTransitionGraphBuilder(
            provider = navController.navigatorProvider,
            startDestination = startDestination,
            route = route,
        ).apply(builder).build()
    }

    Box(modifier = modifier) {
        NavHost(
            navController = navController,
            graph = navGraph,
            contentAlignment = contentAlignment,
            enterTransition = enterTransition,
            exitTransition = exitTransition,
            popEnterTransition = enterTransition,
            popExitTransition = exitTransition,
            modifier = Modifier.fillMaxSize()
        )
        NavTransitionManager.Transition(currentBackStackEntry)
    }

    LaunchedEffect(currentBackStackEntry) {
        currentBackStackEntry?.destination.print("currentBackStackEntry?.destination")
    }
}

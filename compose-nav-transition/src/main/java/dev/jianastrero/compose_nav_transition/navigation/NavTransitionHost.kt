package dev.jianastrero.compose_nav_transition.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost


private val DEFAULT_ENTRY_TRANSITION: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition) =
    { fadeIn(animationSpec = tween(0)) }

private val DEFAULT_EXIT_TRANSITION: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition) =
    { fadeOut(animationSpec = tween(0)) }


@Composable
fun NavTransitionHost(
    navController: NavHostController,
    startDestination: String,
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.Center,
    route: String? = null,
    enterTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition) =
        { fadeIn(animationSpec = tween(700)) },
    exitTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition) =
        { fadeOut(animationSpec = tween(700)) },
    popEnterTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition) =
        enterTransition,
    popExitTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition) =
        exitTransition,
    builder: NavTransitionGraphBuilder.() -> Unit
) {
    val navGraph = remember(navController, startDestination, route, builder) {
        NavTransitionGraphBuilder(
            provider = navController.navigatorProvider,
            startDestination = startDestination,
            route = route
        ).apply(builder).build()
    }

    NavHost(
        navController = navController,
        graph = navGraph,
        modifier = modifier,
        contentAlignment = contentAlignment,
        enterTransition = DEFAULT_ENTRY_TRANSITION,
        exitTransition = DEFAULT_EXIT_TRANSITION,
        popEnterTransition = DEFAULT_ENTRY_TRANSITION,
        popExitTransition = DEFAULT_EXIT_TRANSITION
    )
}

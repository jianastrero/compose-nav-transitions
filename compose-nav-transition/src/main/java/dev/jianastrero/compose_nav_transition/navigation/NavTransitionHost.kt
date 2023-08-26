package dev.jianastrero.compose_nav_transition.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import dev.jianastrero.compose_nav_transition.NavTransitions


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
    builder: NavTransitionGraphBuilder.() -> Unit
) {
    var currentScope: NavTransitionScope? by remember { mutableStateOf(null) }

    val navGraph = remember(navController, startDestination, route, builder) {
        NavTransitionGraphBuilder(
            provider = navController.navigatorProvider,
            startDestination = startDestination,
            route = route,
            onScopeChanged = {
                val previousRoute = currentScope?.route ?: ""
                currentScope = it
                currentScope?.previousRoute = previousRoute
            }
        ).apply(builder).build()
    }

    NavHost(
        navController = navController,
        graph = navGraph,
        modifier = Modifier
            .onGloballyPositioned {
                NavTransitions.hostOffset = it.positionInRoot()
            }
            .then(modifier),
        contentAlignment = contentAlignment,
        enterTransition = DEFAULT_ENTRY_TRANSITION,
        exitTransition = DEFAULT_EXIT_TRANSITION,
        popEnterTransition = DEFAULT_ENTRY_TRANSITION,
        popExitTransition = DEFAULT_EXIT_TRANSITION
    )
}

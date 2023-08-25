package dev.jianastrero.compose_nav_transition.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavigatorProvider
import androidx.navigation.compose.composable
import dev.jianastrero.compose_nav_transition.NavTransition
import dev.jianastrero.compose_nav_transition.animation.NavTransitionContainer

class NavTransitionGraphBuilder(
    provider: NavigatorProvider,
    startDestination: String,
    route: String?
) : NavGraphBuilder(provider, startDestination, route)

fun NavTransitionGraphBuilder.transitionComposable(
    route: String,
    content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit
) {
    composable(route = route) {
        NavTransition.clearCurrentElements()
        NavTransitionContainer(modifier = Modifier.fillMaxSize()) {
            content(it)
        }
    }
}

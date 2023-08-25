package dev.jianastrero.compose_nav_transition.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavigatorProvider
import androidx.navigation.compose.composable
import dev.jianastrero.compose_nav_transition.animation.NavTransitionContainer

class NavTransitionGraphBuilder(
    provider: NavigatorProvider,
    startDestination: String,
    route: String?
) : NavGraphBuilder(provider, startDestination, route)

fun NavTransitionGraphBuilder.transitionComposable(
    route: String,
    content: @Composable NavTransitionScope.(NavBackStackEntry) -> Unit
) {
    composable(route = route) { navBackStackEntry ->
        val scope = remember(route) {
            NavTransitionScope(route)
        }

        scope.NavTransitionContainer(modifier = Modifier.fillMaxSize()) {
            scope.content(navBackStackEntry)
        }
    }
}

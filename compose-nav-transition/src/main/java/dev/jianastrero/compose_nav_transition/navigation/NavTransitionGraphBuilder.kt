package dev.jianastrero.compose_nav_transition.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavigatorProvider
import androidx.navigation.compose.composable
import dev.jianastrero.compose_nav_transition.animation.NavTransitionContainer

class NavTransitionGraphBuilder(
    provider: NavigatorProvider,
    startDestination: String,
    route: String?,
    internal val onScopeChanged: (NavTransitionScope) -> Unit
) : NavGraphBuilder(provider, startDestination, route)

fun NavTransitionGraphBuilder.transitionComposable(
    route: String,
    content: @Composable NavTransitionScope.(NavBackStackEntry) -> Unit
) {
    composable(route = route) { navBackStackEntry ->
        val lifecycleOwner = LocalLifecycleOwner.current
        val scope = remember(route) {
            NavTransitionScope(route)
        }

        scope.NavTransitionContainer(modifier = Modifier.fillMaxSize()) {
            scope.content(navBackStackEntry)
        }

        DisposableEffect(lifecycleOwner) {
            val observer = LifecycleEventObserver { _, event ->
                if (event == Lifecycle.Event.ON_START) {
                    onScopeChanged(scope)
                }
            }

            lifecycleOwner.lifecycle.addObserver(observer)

            onDispose {
                lifecycleOwner.lifecycle.removeObserver(observer)
            }
        }
    }
}

package dev.jianastrero.compose_nav_transition.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavigatorProvider

class NavTransitionGraphBuilder(
    provider: NavigatorProvider,
    startDestination: String,
    route: String?
) : NavGraphBuilder(provider, startDestination = startDestination, route = route)

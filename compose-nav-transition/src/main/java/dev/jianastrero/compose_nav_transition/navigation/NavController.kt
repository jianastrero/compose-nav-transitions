package dev.jianastrero.compose_nav_transition.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder
import dev.jianastrero.compose_nav_transition.NavTransitions


fun NavController.navigateTransition(route: String, builder: NavOptionsBuilder.() -> Unit = {}) {
    NavTransitions.lastRoute = currentDestination?.route ?: ""
    navigate(route, builder)
}

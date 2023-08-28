package dev.jianastrero.compose_nav_transition.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import dev.jianastrero.compose_nav_transition.example.nav_graph.MainNavGraph
import dev.jianastrero.compose_nav_transition.navigation.NavTransitionHost

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            NavTransitionHost(
                navController = navController,
                startDestination = "Home",
                modifier = Modifier.fillMaxSize()
            ) {
                MainNavGraph(
                    navigate = navController::navigate,
                    back = navController::popBackStack,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

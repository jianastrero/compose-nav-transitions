package dev.jianastrero.compose_nav_transition.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import dev.jianastrero.compose_nav_transition.example.screens.FirstScreen
import dev.jianastrero.compose_nav_transition.example.screens.SecondScreen
import dev.jianastrero.compose_nav_transition.example.screens.ScreenState

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var screen: ScreenState by rememberSaveable { mutableStateOf(ScreenState.FirstScreen) }

            AnimatedVisibility(
                visible = screen == ScreenState.FirstScreen,
                enter = fadeIn(tween(600)),
                exit = fadeOut(tween(600))
            ) {
                FirstScreen(
                    onGotoSecondScreen = { screen = ScreenState.SecondScreen },
                    modifier = Modifier.fillMaxSize()
                )
            }
            AnimatedVisibility(
                visible = screen == ScreenState.SecondScreen,
                enter = fadeIn(tween(600)),
                exit = fadeOut(tween(600))
            ) {
                SecondScreen(
                    onGotoFirstScreen = { screen = ScreenState.FirstScreen },
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

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
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import dev.jianastrero.compose_nav_transition.element.Element
import dev.jianastrero.compose_nav_transition.example.screens.FirstScreen
import dev.jianastrero.compose_nav_transition.example.screens.ScreenState
import dev.jianastrero.compose_nav_transition.example.screens.SecondScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var screen: ScreenState by rememberSaveable { mutableStateOf(ScreenState.FirstScreen) }
            var sharedImageElement: Element by remember { mutableStateOf(Element.None) }
            var sharedLabelElement: Element by remember { mutableStateOf(Element.None) }

            AnimatedVisibility(
                visible = screen == ScreenState.FirstScreen,
                enter = fadeIn(tween(600)),
                exit = fadeOut(tween(600))
            ) {
                FirstScreen(
                    sharedImageElement = sharedImageElement,
                    sharedLabelElement = sharedLabelElement,
                    onGotoSecondScreen = { image, label ->
                        sharedImageElement = image
                        sharedLabelElement = label
                        screen = ScreenState.SecondScreen
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }
            AnimatedVisibility(
                visible = screen == ScreenState.SecondScreen,
                enter = fadeIn(tween(600)),
                exit = fadeOut(tween(600))
            ) {
                SecondScreen(
                    sharedImageElement = sharedImageElement,
                    sharedLabelElement = sharedLabelElement,
                    onGotoFirstScreen = { image, label ->
                        sharedImageElement = image
                        sharedLabelElement = label
                        screen = ScreenState.FirstScreen
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}
